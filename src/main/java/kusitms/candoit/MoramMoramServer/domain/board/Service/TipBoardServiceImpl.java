package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TipBoardServiceImpl implements TipBoardService {

    private final ModelMapper modelMapper;

    private final TipBoardRepository tipBoardRepository;

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

}
