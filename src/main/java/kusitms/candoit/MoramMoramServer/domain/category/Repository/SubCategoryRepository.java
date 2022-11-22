package kusitms.candoit.MoramMoramServer.domain.category.Repository;

import kusitms.candoit.MoramMoramServer.domain.category.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.category.Entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    SubCategory findSubCategoryByUserIdAndBoardTypeAndBoardId(Long userId, String boardType, Long boardId);
}
