package kusitms.candoit.MoramMoramServer.domain.category.Controller;

import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.SubCategory;
import kusitms.candoit.MoramMoramServer.domain.category.Service.CategoryService;
import kusitms.candoit.MoramMoramServer.domain.category.Service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    // 카테고리 조회
    @GetMapping("/sub-category")
    public ResponseEntity<?> getSubCategory(String type, Long b_id){
        SubCategory subCategory = subCategoryService.getSubCategory(type, b_id);
        return new ResponseEntity<>(subCategory, HttpStatus.OK);
    }

}
