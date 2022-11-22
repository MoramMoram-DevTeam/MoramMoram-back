package kusitms.candoit.MoramMoramServer.domain.application.Controller;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.category.Controller.CategoryController;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.application.Service.ApplicationService;
import kusitms.candoit.MoramMoramServer.domain.category.Service.CategoryService;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CategoryService categoryService;

    // 신청서 작성하기
    @PostMapping("/new")
    public ResponseEntity<?> newApplication(@RequestBody ApplicationDto reqBody){

        Application applicationEntity = applicationService.newApplication(reqBody);

        Category c = categoryService.newCategory(applicationEntity);
        applicationEntity.setCategoryId(c.getCategoryId());

        return new ResponseEntity<>(applicationEntity, HttpStatus.CREATED);
    }

    // 신청서 수정하기
    @PatchMapping("/edit/{applicationId}")
    public BaseResponse<?> editApplication(@RequestBody ApplicationDto reqBody, @PathVariable Long applicationId){
        applicationService.editApplication(reqBody, applicationId);
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

}
