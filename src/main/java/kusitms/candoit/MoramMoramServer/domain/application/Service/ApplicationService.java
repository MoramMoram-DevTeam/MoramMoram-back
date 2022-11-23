package kusitms.candoit.MoramMoramServer.domain.application.Service;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Repository.ApplicationRepository;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.FleamarketRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final FleamarketRepository fleamarketRepository;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    @Transactional
    public Application newApplication(Long m_id, ApplicationDto applicationDto){

        Fleamarket fleamarket = fleamarketRepository.findById(m_id).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        });

        Application app = applicationDto.toEntity();
        app.setUserId(getTokenInfo().getId());
        app.setMarketId(fleamarket.getId());

        // 이미지 처리 필요

        return applicationRepository.save(app);
    }


    @Transactional
    public Application editApplication(Application appDto, Long appId){

        Long userId = getTokenInfo().getId();

        Application app = applicationRepository.findApplicationByApplicationIdAndUserId(appId, userId);

        if (app == null){
            throw new CustomException(CustomErrorCode.APPLICATION_NO_EXIST);
        }
        if (getTokenInfo().getId() != app.getUserId()) {
            throw new CustomException(CustomErrorCode.NO_AUTHORITY);
        }

        app.setStoreName(appDto.getStoreName());
        app.setOnlineChannel(appDto.getOnlineChannel());
        app.setReturnAddress(appDto.getReturnAddress());

        app.setCategory1(appDto.isCategory1());
        app.setCategory2(appDto.isCategory2());
        app.setCategory3(appDto.isCategory3());
        app.setCategory4(appDto.isCategory4());

        app.setSubCategory1(appDto.getSubCategory1());
        app.setSubCategory2(appDto.getSubCategory2());
        app.setSubCategory3(appDto.getSubCategory3());
        app.setSubCategory4(appDto.getSubCategory4());
        app.setSubCategory5(appDto.getSubCategory5());

        app.setStall(appDto.isStall());
        app.setShelf(appDto.isShelf());
        app.setLight(appDto.isLight());
        app.setWrapping(appDto.isWrapping());
        app.setHanger(appDto.isHanger());
        app.setMannequin(appDto.isMannequin());
        app.setMirror(appDto.isMirror());
        app.setNone(appDto.isNone());

        app.setMarketExp(appDto.getMarketExp());
        app.setOnlineExp(appDto.isOnlineExp());
        app.setPriceAvg(appDto.getPriceAvg());
        app.setRequest(appDto.getRequest());
//        app.setUtensil(appDto.getUtensil());

        // 이미지 처리 필요
//         certificateImg, itemImg

        return applicationRepository.save(app);
    }

    @Transactional
    public void deleteApplication(Long appId){

        Long userId = getTokenInfo().getId();

        Application app = applicationRepository.findApplicationByApplicationIdAndUserId(appId, userId);

        if (app == null) {
            throw new CustomException(CustomErrorCode.APPLICATION_NO_EXIST);
        }
        if (getTokenInfo().getId() == app.getUserId()) {
            applicationRepository.delete(app);
        } else {
            throw new CustomException(CustomErrorCode.NO_AUTHORITY);
        }
    }


    // 내 신청서 모아보기
    public List<Application> getMyApplications(){
        Long userId = getTokenInfo().getId();
        List<Application> applicationList = applicationRepository.findAllByUserId(userId);
        return applicationList;
    }

    public Application getMyApp(Long appId){
        Long userId = getTokenInfo().getId();
        Application applicationEntity = applicationRepository.findApplicationByApplicationIdAndUserId(appId, userId);
        return applicationEntity;
    }

    public List<Application> appListCompany(Long m_id){
        Long userId = getTokenInfo().getId();
        List<Application> applicationList = applicationRepository.findAllByMarketId(m_id);
        return applicationList;
    }

}
