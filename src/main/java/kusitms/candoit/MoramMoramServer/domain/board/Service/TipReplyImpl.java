package kusitms.candoit.MoramMoramServer.domain.board.Service;


import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionReply;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipReply;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionReplyRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipReplyImpl implements TipReplyService {

    private final TipReplyRepository tipReplyRepository;

    private final TipBoardRepository tipBoardRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(TipReplyDTO tipReplyDTO) {
        TipReply reply = modelMapper.map(tipReplyDTO, TipReply.class);

        //댓글 수 ++
        reply.getTipBoard().updateReplyCnt();
        tipBoardRepository.save(reply.getTipBoard());
        //댓글 생성
        Long id = tipReplyRepository.save(reply).getTipReplyId();

        return id;
    }

    @Override
    public void remove(Long replyId) {

        tipReplyRepository.deleteById(replyId);
    }

}
