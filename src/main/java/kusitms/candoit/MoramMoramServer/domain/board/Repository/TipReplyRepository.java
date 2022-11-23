package kusitms.candoit.MoramMoramServer.domain.board.Repository;

import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionReply;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipReplyRepository extends JpaRepository<TipReply, Long> {

    @Query("select r from TipReply r where r.status='ACTIVE' and r.tipBoard.tipBoardId = :id")
    Page<TipReply> listOfBoard(Long id, Pageable pageable);

    @Modifying
    @Query("delete from TipReply r where r.tipBoard.tipBoardId = :id")
    void deleteAllByTipBoard(@Param("id") Long id);


}
