package kusitms.candoit.MoramMoramServer.domain.category.Repository;

import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByUserIdAndBoardTypeAndBoardId(Long userId, String boardType, Long boardId);
}
