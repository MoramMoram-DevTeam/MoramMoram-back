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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TipReplyDTO> getReplyList(int page, Long postId) {
        Page<TipReply> replies = tipReplyRepository.listOfBoard(postId, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));

        List<TipReplyDTO> results = replies.getContent().stream().map(TipReply ->
                modelMapper.map(TipReply, TipReplyDTO.class)
        ).collect(Collectors.toList());
        return results;
    }

}
