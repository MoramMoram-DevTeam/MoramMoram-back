package kusitms.candoit.MoramMoramServer.domain.category.Service;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.SubCategory;
import kusitms.candoit.MoramMoramServer.domain.category.Repository.SubCategoryRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
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
    public SubCategory editSubCategory(Application application){

        SubCategory subCategory = subCategoryRepository.findBySubCategoryId(application.getCategoryId());

        subCategory.setSubCategory1(application.getSubCategory1());
        subCategory.setSubCategory2(application.getSubCategory2());
        subCategory.setSubCategory3(application.getSubCategory3());
        subCategory.setSubCategory4(application.getSubCategory4());
        subCategory.setSubCategory5(application.getSubCategory5());

        return subCategoryRepository.save(subCategory);

    }


    @Transactional
    public SubCategory newSubCategory(Application application){
        SubCategory subCategory = new SubCategory();

        subCategory.setSubCategory1(application.getSubCategory1());
        subCategory.setSubCategory2(application.getSubCategory2());
        subCategory.setSubCategory3(application.getSubCategory3());
        subCategory.setSubCategory4(application.getSubCategory4());
        subCategory.setSubCategory5(application.getSubCategory5());

        subCategory.setBoardType("Application");
        subCategory.setBoardId(application.getApplicationId());
        subCategory.setUserId(application.getUserId());

        return subCategoryRepository.save(subCategory);
    }

    @Transactional
    public SubCategory getSubCategory(String type, Long b_id){
        Long userId = getTokenInfo().getId();
        SubCategory subCategory = subCategoryRepository.findSubCategoryByUserIdAndBoardTypeAndBoardId(userId, type, b_id);
        return subCategory;
    }

}
