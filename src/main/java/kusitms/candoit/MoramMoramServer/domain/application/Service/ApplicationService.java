package kusitms.candoit.MoramMoramServer.domain.application.Service;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Repository.ApplicationRepository;
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

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    @Transactional
    public Application newApplication(ApplicationDto applicationDto){
        boolean test = applicationDto.isCategory1();
        log.info("------카테고리 테스트(set before): " + String.valueOf(test));

        Application app = applicationDto.toEntity();

        app.setUserId(getTokenInfo().getId());

        log.info("------카테고리 테스트(set after): " + String.valueOf(test));



//        app.setCategory(categoryDto.toEntity());
//        app.setUserId(getTokenInfo().getId());

//        Category category = categoryDto.toEntity();
//        category.setUserId(getTokenInfo().getId());

//        app.setCategory(category);

        // 이미지 처리 필요

        return applicationRepository.save(app);
    }


    @Transactional
    public Application editApplication(ApplicationDto appDto, Long appId){

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
//        app.setCategory(appDto.getCategory());
//        app.setSubCategory(appDto.getSubCategory());
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

}
