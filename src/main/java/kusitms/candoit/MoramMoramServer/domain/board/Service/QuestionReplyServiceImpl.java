package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.*;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionReply;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionReplyServiceImpl implements QuestionReplyService{
    private final QuestionReplyRepository questionReplyRepository;
    private final ModelMapper modelMapper;

    private final QuestionBoardRepository questionBoardRepository;

    @Override
    public Long register(QuestionReplyDTO questionReplyDTO){

        QuestionReply reply = modelMapper.map(questionReplyDTO, QuestionReply.class);

        //댓글 수 ++
        reply.getQuestionBoard().updateReplyCnt();
        questionBoardRepository.save(reply.getQuestionBoard());
        //댓글 생성
        Long id = questionReplyRepository.save(reply).getQuestionReplyId();

        return id;
    }

    @Override
    public void remove(Long replyId) {

        questionReplyRepository.deleteById(replyId);
    }

    @Override
    public List<QuestionReplyDTO> getReplyList(int page, Long questionBoardId) {
        Page<QuestionReply> replies = questionReplyRepository.listOfBoard(questionBoardId, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));

        List<QuestionReplyDTO> results = replies.getContent().stream().map(QuestionReply ->
                modelMapper.map(QuestionReply, QuestionReplyDTO.class)
        ).collect(Collectors.toList());
        return results;
    }

//    @Override
//    public PageResponseDTO<QuestionReplyPagingDTO> getListOfBoard(Long questionBoardId, PageRequestDTO pageRequestDTO) {
//        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0:
//                        pageRequestDTO.getPage()-1,
//                pageRequestDTO.getSize(),
//                Sort.by("updatedAt").ascending()
//        );
//
//        Page<QuestionReply> result = questionReplyRepository.listOfBoard(questionBoardId, pageable);
//
//        List<QuestionReplyPagingDTO> dtoList = result.getContent().stream().map(QuestionReply -> modelMapper.map(
//                        QuestionReply, QuestionReplyPagingDTO.class))
//                .collect(Collectors.toList());
//
//        return PageResponseDTO.<QuestionReplyDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
//    }
}
