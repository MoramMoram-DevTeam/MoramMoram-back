package kusitms.candoit.MoramMoramServer.domain.application.Controller;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Repository.ApplicationRepository;
import kusitms.candoit.MoramMoramServer.domain.category.Controller.CategoryController;
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

import javax.validation.Valid;
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
    public ResponseEntity<?> newApplication(Long m_id, @RequestBody ApplicationDto reqBody){

        Application applicationEntity = applicationService.newApplication(m_id, reqBody);

        Category c = categoryService.newCategory(applicationEntity);
        SubCategory s = subCategoryService.newSubCategory(applicationEntity);

        applicationEntity.setCategoryId(c.getCategoryId());
        applicationEntity.setSubCategoryId(s.getSubCategoryId());
        applicationRepository.save(applicationEntity);

        return new ResponseEntity<>(applicationEntity, HttpStatus.CREATED);
    }

    // 신청서 수정하기
    @PatchMapping("/edit/{applicationId}")
    public BaseResponse<?> editApplication(@RequestBody Application reqBody, @PathVariable Long applicationId){
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

}
