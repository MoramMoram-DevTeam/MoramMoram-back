package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardLikeDTO;

import java.util.List;

public interface TipBoardService {

    //게시글 등록
    Long register(TipBoardDTO tipBoardDTO);

    void modify(Long tipBoardId,TipBoardDTO tipBoardDTO);

    void deleteOne(Long tipBoardId);

    //게시글 자세히보기
    TipBoardDTO readOne(Long tipBoardId);

    List<TipBoardDTO> getBoard(int page);

    Long like(Long tipBoardId, TipBoardLikeDTO tipBoardLikeDTO);

    List<TipBoardDTO> getTopPosts();

    void deleteReplies(Long tipBoardId);
}
