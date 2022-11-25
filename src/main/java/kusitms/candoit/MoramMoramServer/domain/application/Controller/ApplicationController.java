package kusitms.candoit.MoramMoramServer.domain.application.Controller;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Repository.ApplicationRepository;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.application.Service.ApplicationService;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.SubCategory;
import kusitms.candoit.MoramMoramServer.domain.category.Service.CategoryService;
import kusitms.candoit.MoramMoramServer.domain.category.Service.SubCategoryService;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final ApplicationRepository applicationRepository;

    // 신청서 작성하기
    @PostMapping("/new")
    public ResponseEntity<?> newApplication(Long m_id, @RequestBody ApplicationDto reqBody) {

//        String img1 = "";
//        String img2 = "";
//
//        if(multipartFile != null && !multipartFile.isEmpty()){
//            img1 = applicationService.uploadImage(multipartFile);
//        }
//
//        if(multipartFile2 != null && !multipartFile2.isEmpty()){
//            img2 = applicationService.uploadImage(multipartFile2);
//        }

        Application applicationEntity = applicationService.newApplication(m_id, reqBody);

        Category c = categoryService.newCategory(applicationEntity);
        SubCategory s = subCategoryService.newSubCategory(applicationEntity);

        applicationEntity.setCategoryId(c.getCategoryId());
        applicationEntity.setSubCategoryId(s.getSubCategoryId());
        applicationEntity.setStatus("WAITING");

        applicationRepository.save(applicationEntity);

        return new ResponseEntity<>(applicationEntity, HttpStatus.CREATED);
    }

    // 신청서 수정하기
    @PatchMapping("/edit/{applicationId}")
    public BaseResponse<?> editApplication(@RequestBody Application reqBody, @PathVariable Long applicationId) {

//        String img1 = "";
//        String img2 = "";
//
//        if(multipartFile != null && !multipartFile.isEmpty()){
//            img1 = applicationService.uploadImage(multipartFile);
//        }
//
//        if(multipartFile2 != null && !multipartFile2.isEmpty()){
//            img2 = applicationService.uploadImage(multipartFile2);
//        }

        Application application = applicationService.editApplication(reqBody, applicationId);
        categoryService.editCategory(application);
//        subCategoryService.editSubCategory(application);
        return new BaseResponse<>(applicationId);
    }

    // 신청서 삭제하기
    @DeleteMapping("/delete/{applicationId}")
    public BaseResponse<?> deleteApplication(@PathVariable Long applicationId){
        applicationService.deleteApplication(applicationId);
        return new BaseResponse<>(applicationId);
    }

    // 내 신청서 모아보기
    @GetMapping("")
    public ResponseEntity<?> myApplications(){
        List<Application> apps = applicationService.getMyApplications();
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    // 신청서 상세보기
    @GetMapping("/{applicationId}")
    public ResponseEntity<?> myApp(@PathVariable Long applicationId){
        Application app = applicationService.getMyApp(applicationId);
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    // 마켓별 신청서 보기
    @GetMapping("/company")
    public  ResponseEntity<?> applicationsByMarket(Long m_id){
        List<Application> applicationList = applicationService.appListCompany(m_id);
        return new ResponseEntity<>(applicationList, HttpStatus.OK);
    }

    // 신청 승인하기
    @PostMapping("/approved/{applicationId}")
    public BaseResponse<?> approveApplication(@PathVariable Long applicationId){
        applicationService.approveApplication(applicationId);
        return new BaseResponse<>(applicationId);
    }
    // 신청 거절하기
    @PostMapping("/rejected/{applicationId}")
    public BaseResponse<?> rejectApplication(@PathVariable Long applicationId){
        applicationService.rejectApplication(applicationId);
        return new BaseResponse<>(applicationId);
    }
    // 마켓별 승인된 신청서
    @GetMapping("/approved-markets")
    public ResponseEntity<?> approvedApplication(Long m_id){
        log.info("----실행은 되나요: " + m_id);
        List<Application> applicationList = applicationService.approvedApps(m_id);
        log.info("----service는 도나요: " + applicationList.get(0).getApplicationId());
        return new ResponseEntity<>(applicationList, HttpStatus.OK);
    }


}
