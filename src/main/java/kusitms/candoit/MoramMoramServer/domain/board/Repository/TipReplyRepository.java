package kusitms.candoit.MoramMoramServer.domain.board.Repository;

import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipReplyRepository extends JpaRepository<TipReply, Long> {

    @Query("select r from TipReply r where r.tipBoard.tipBoardId = :id")
    Page<TipReply> listOfBoard(Long id, Pageable pageable);
 }
