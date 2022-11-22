package kusitms.candoit.MoramMoramServer.domain.category.Service;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.category.Repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
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
    public Category newCategory(Application application){
        Category category = new Category();

        category.setCraft1(application.isCategory1());
        category.setCraft2(application.isCategory2());
        category.setCraft3(application.isCategory3());
        category.setCraft4(application.isCategory4());

        category.setBoardType("Application");
        category.setBoardId(application.getApplicationId());
        category.setUserId(application.getUserId());

        return categoryRepository.save(category);
    }

    @Transactional
    public Category editCategory(Application application){

        Category category = categoryRepository.findByCategoryId(application.getCategoryId());

        category.setCraft1(application.isCategory1());
        category.setCraft2(application.isCategory2());
        category.setCraft3(application.isCategory3());
        category.setCraft4(application.isCategory4());

        return categoryRepository.save(category);

    }

    @Transactional
    public Category getCategory(String type, Long b_id){
        Long userId = getTokenInfo().getId();
        Category category = categoryRepository.findCategoryByUserIdAndBoardTypeAndBoardId(userId, type, b_id);
        return category;
    }
}
