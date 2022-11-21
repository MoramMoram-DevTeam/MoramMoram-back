package kusitms.candoit.MoramMoramServer.domain.board.Repository;

import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, Long> {

    @Query("select r from QuestionReply r where r.questionBoard.questionBoardId = :id")
    Page<QuestionReply> listOfBoard(Long id, Pageable pageable);

//    @Query(value = "")
//    void deleteAllBy(int questionBoardId);
}
