package kusitms.candoit.MoramMoramServer.domain.category.Controller;

import kusitms.candoit.MoramMoramServer.domain.application.Dto.ApplicationDto;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Service.ApplicationService;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
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
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 조회
    @GetMapping("/category")
    public ResponseEntity<?> getCategory(String type, Long b_id){
        Category category = categoryService.getCategory(type, b_id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
