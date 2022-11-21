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

}
