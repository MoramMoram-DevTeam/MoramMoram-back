package kusitms.candoit.MoramMoramServer.domain.application.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final FleamarketRepository fleamarketRepository;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    @Transactional
    public Application newApplication(Long m_id, ApplicationDto applicationDto, String img1, String img2) {

        Fleamarket fleamarket = fleamarketRepository.findById(m_id).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        });

        Application app = applicationDto.toEntity();
        app.setUserId(getTokenInfo().getId());
        app.setUserName(getTokenInfo().getName());
        app.setMarketId(fleamarket.getId());

        app.setItemImg(img1);
        app.setCertificateImg(img2);

        return applicationRepository.save(app);
    }


    @Transactional
    public Application editApplication(Application appDto, Long appId, String img1, String img2){

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
        app.setCategory5(appDto.isCategory5());
        app.setCategory6(appDto.isCategory6());
        app.setCategory7(appDto.isCategory7());
        app.setCategory8(appDto.isCategory8());
        app.setCategory9(appDto.isCategory9());
        app.setCategory10(appDto.isCategory10());

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
        app.setItemImg(img1);
        app.setCertificateImg(img2);

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

    public Application approveApplication(Long applicationId){
        Application application = applicationRepository.findApplicationByApplicationId(applicationId);
        application.setStatus("APPROVED");
        applicationRepository.save(application);
        return application;
    }

    public Application rejectApplication(Long applicationId){
        Application application = applicationRepository.findApplicationByApplicationId(applicationId);
        application.setStatus("REJECTED");
        applicationRepository.save(application);
        return application;
    }

    public List<Application> approvedApps(Long m_id){
        String status = "APPROVED";
        List<Application> applicationList = applicationRepository.findAllByMarketIdAndStatus(m_id, status);
        return applicationList;
    }

    //이미지 넣기
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        //이미지 업로드
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID()+toString();
        String fileName = uuid+"_"+multipartFile.getOriginalFilename();
        String question_board_image_name = "applciations/" + now+"/"+ fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, question_board_image_name, multipartFile.getInputStream(), objMeta);

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
    }

}
