package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoardLike;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipBoardLike;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipBoardLikeRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipReplyRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
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

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NO_AUTHORITY;
import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.POST_NO_EXIST;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TipBoardServiceImpl implements TipBoardService {

    private final ModelMapper modelMapper;

    private final TipBoardRepository tipBoardRepository;

    private final TipReplyRepository tipReplyRepository;

    private final TipBoardLikeRepository tipBoardLikeRepository;

    @Override
    public Long register(TipBoardDTO tipBoardDTO) {
        TipBoard board = modelMapper.map(tipBoardDTO, TipBoard.class);
        Long tipBoardId = tipBoardRepository.save(board).getTipBoardId();

        return tipBoardId;
    }

    @Override
    public void modify(Long tipBoardId, TipBoardDTO tipBoardDTO) {
        Optional<TipBoard> result = tipBoardRepository.findById(tipBoardId);

        result.ifPresent( board ->{
            if(tipBoardDTO.getTitle() != null){
                board.changTitle(tipBoardDTO.getTitle());
                log.info("제목 바꾸고 나서 -> "+board.getUpdatedAt());
                log.info("맨처음 ->  "+board.getBoardDate());
            }
            if(tipBoardDTO.getNote()!=null){
                board.changeNote(tipBoardDTO.getNote());
                log.info("내용 바꾸고 나서 -> "+board.getUpdatedAt());
            }
            if(tipBoardDTO.getImg() !=null ){
                board.changeImg(tipBoardDTO.getImg());
            }
            //생성일 update
            board.updateBoardDate();
            log.info("최종 ->  "+board.getBoardDate());
            tipBoardRepository.save(board);
        });
    }

    @Override
    public void deleteOne(Long tipBoardId) {
        Optional<TipBoard> result = tipBoardRepository.findById(tipBoardId);
        TipBoard board = result.orElseThrow();

        board.updateStatus();
        tipBoardRepository.save(board);
    }

    //게시글 보기
    @Override
    public TipBoardDTO readOne(Long tipBoardId) {
        Optional<TipBoard> result = tipBoardRepository.findById(tipBoardId);
        TipBoard board = result.orElseThrow();
        //status 값 확인
        log.info("============status===========");
        log.info(board.getStatus());
        if(board.getStatus().equals("DELETED")){
            log.info("status확인");
            throw new CustomException(POST_NO_EXIST);
        }
        //조회 수 추가
        board.updateViewCnt();
        tipBoardRepository.save(board);
        TipBoardDTO boardDTO = modelMapper.map(board, TipBoardDTO.class);

        return boardDTO;
    }

    @Override
    public List<TipBoardDTO> getBoard(int page) {
        Page<TipBoard> boards = tipBoardRepository.findAllByStatus(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "boardDate")), "ACTIVE");

        List<TipBoardDTO> results = boards.getContent().stream().map(TipBoard ->
                modelMapper.map(TipBoard, TipBoardDTO.class)
        ).collect(Collectors.toList());

        return results;
    }

    @Override
    public Long like(Long tipBoardId, TipBoardLikeDTO tipBoardLikeDTO) {
        //게시글 likeCnt update
        Optional<TipBoard> result = tipBoardRepository.findById(tipBoardId);
        TipBoard board = result.orElseThrow();

        board.updateLike();
        tipBoardRepository.save(board);
        log.info("성공2");
        //테이블 생성
        TipBoardLike like = modelMapper.map(tipBoardLikeDTO, TipBoardLike.class);
        Long likeId = tipBoardLikeRepository.save(like).getLikeId();
        log.info("성공3");
        return likeId;
    }

    @Override
    public List<TipBoardDTO> getTopPosts() {
        List<TipBoard> result = tipBoardRepository.findTop();

        List<TipBoardDTO> topBoard = result.stream()
                .map(m-> modelMapper.map(m, TipBoardDTO.class))
                .collect(Collectors.toList());
        return topBoard;
    }

    @Override
    public void deleteReplies(Long tipBoardId) {
        tipReplyRepository.deleteAllByTipBoard(tipBoardId);
    }

}
