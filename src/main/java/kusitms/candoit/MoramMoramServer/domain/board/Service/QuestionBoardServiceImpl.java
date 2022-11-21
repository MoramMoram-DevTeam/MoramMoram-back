package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoardLike;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardLikeRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class QuestionBoardServiceImpl implements QuestionBoardService {

    private final ModelMapper modelMapper;
    private final QuestionBoardRepository questionBoardRepository;

    private final QuestionBoardLikeRepository questionBoardLikeRepository;

    @Override
    public Long register(QuestionBoardDTO questionBoardDTO) {
        log.info("여기 까지 오나요?");
        log.info(questionBoardDTO.toString());
        QuestionBoard board = modelMapper.map(questionBoardDTO, QuestionBoard.class);
        Long questionBoardId = questionBoardRepository.save(board).getQuestionBoardId();

        return questionBoardId;
    }

    @Override
    public QuestionBoardDTO readOne(Long questionBoardId) {

        Optional<QuestionBoard> result = questionBoardRepository.findById(questionBoardId);
        QuestionBoard board = result.orElseThrow();
        //조회 수 추가
        board.updateViewCnt();
        questionBoardRepository.save(board);
        QuestionBoardDTO boardDTO = modelMapper.map(board, QuestionBoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(Long questionBoardId, QuestionBoardDTO questionBoardDTO) {
        Optional<QuestionBoard> result = questionBoardRepository.findById(questionBoardId);

        //TODO 수정보완

        result.ifPresent( board ->{
            if(questionBoardDTO.getTitle() != null){
                board.changTitle(questionBoardDTO.getTitle());
                log.info("제목 바꾸고 나서 -> "+board.getUpdatedAt());
                log.info("맨처음 ->  "+board.getBoardDate());
            }
            if(questionBoardDTO.getNote()!=null){
                board.changeNote(questionBoardDTO.getNote());
                log.info("내용 바꾸고 나서 -> "+board.getUpdatedAt());
            }
            if(questionBoardDTO.getImg() !=null ){
                board.changeImg(questionBoardDTO.getImg());
            }
            //생성일 update
            board.updateBoardDate();
            log.info("최종 ->  "+board.getBoardDate());
            questionBoardRepository.save(board);
        });
    }

    @Override
    public void remove(Long questionBoardId) {
        questionBoardRepository.deleteById(questionBoardId);
    }

    @Override
    public List<QuestionBoardDTO> getBoard(int page) {
        Page<QuestionBoard> boards = questionBoardRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "boardDate")));

        List<QuestionBoardDTO> results = boards.getContent().stream().map(QuestionBoard ->
                modelMapper.map(QuestionBoard, QuestionBoardDTO.class)
        ).collect(Collectors.toList());

        return results;
    }

    @Override
    public Long like(Long questionBoardId,  QuestionBoardLikeDTO questionBoardLikeDTO) {
        //게시글 likeCnt update
        Optional<QuestionBoard> result = questionBoardRepository.findById(questionBoardId);
        QuestionBoard board = result.orElseThrow();

        board.updateLike();
        questionBoardRepository.save(board);
        log.info("성공2");
        //테이블 생성
        QuestionBoardLike like = modelMapper.map(questionBoardLikeDTO, QuestionBoardLike.class);
        Long likeId = questionBoardLikeRepository.save(like).getLikeId();
        log.info("성공3");
        return likeId;

    }

    @Override
    public List<QuestionBoardDTO> getTopPosts() {
       List<QuestionBoard> result = questionBoardRepository.findTop();
       log.info("자자 여기는 들어왔나요?");
       List<QuestionBoardDTO> topBoard = result.stream()
                .map(m-> modelMapper.map(m, QuestionBoardDTO.class))
                .collect(Collectors.toList());

        //List<QuestionBoardDTO> resultList = result.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());
        return topBoard;
    }


}
