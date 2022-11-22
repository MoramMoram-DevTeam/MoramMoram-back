package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipReplyDTO;

import java.util.List;

public interface TipReplyService {

    Long register(TipReplyDTO tipReplyDTO);

    //void remove(Long replyId);

    //List<QuestionReplyDTO> getReplyList(int page, Long questionBoardId);
}
