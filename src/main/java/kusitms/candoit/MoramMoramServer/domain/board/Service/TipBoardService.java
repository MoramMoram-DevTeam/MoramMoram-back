package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardDTO;

public interface TipBoardService {

    //게시글 등록
    Long register(TipBoardDTO tipBoardDTO);

    void modify(Long tipBoardId,TipBoardDTO tipBoardDTO);
}
