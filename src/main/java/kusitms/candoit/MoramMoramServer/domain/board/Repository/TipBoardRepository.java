package kusitms.candoit.MoramMoramServer.domain.board.Repository;

import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipBoardRepository extends JpaRepository<TipBoard, Long> {
    Page<TipBoard> findAllByStatus(Pageable pageable, String status);

    @Query(value="select tip_board_id, created_at, updated_at, board_date, img, like_cnt, name, note, reply_cnt, status, title, user_id, view_cnt from tip_board\n" +
            "         where created_at > DATE_ADD(now(), INTERVAL -7 DAY)\n" +
            "         order by like_cnt desc,view_cnt desc  limit 5;\n", nativeQuery = true)
    List<TipBoard> findTop();
}
