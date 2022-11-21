package kusitms.candoit.MoramMoramServer.domain.board.Service;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;

import java.util.List;

public interface QuestionBoardService {

    Long register(QuestionBoardDTO questionBoardDTO);

    QuestionBoardDTO readOne(Long questionBoardId);

    void modify(Long questionBoardId,QuestionBoardDTO questionBoardDTO);

    void remove(Long questionBoardId);

    List<QuestionBoardDTO> getBoard(int page);

    Long like(Long questionBoardId, QuestionBoardLikeDTO questionBoardLikeDTO);

    List<QuestionBoardDTO> getTopPosts();
}
