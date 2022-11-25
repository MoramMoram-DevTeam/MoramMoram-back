package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.*;

import java.util.List;

public interface QuestionReplyService {

    Long register(QuestionReplyDTO questionReplyDTO);

    //PageResponseDTO<QuestionReplyPagingDTO> getListOfBoard(Long questionBoardId,
     //                                                      PageRequestDTO pageRequestDTO);
    void remove(Long replyId);

    List<QuestionReplyDTO> getReplyList(int page, Long questionBoardId);
}
