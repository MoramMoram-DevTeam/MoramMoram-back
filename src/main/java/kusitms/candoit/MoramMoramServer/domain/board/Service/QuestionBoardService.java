package kusitms.candoit.MoramMoramServer.domain.board.Service;

import ch.qos.logback.core.status.Status;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionBoardService {

    Long register(QuestionBoardDTO questionBoardDTO, String img);

    QuestionBoardDTO readOne(Long questionBoardId);

    void modify(Long questionBoardId,QuestionBoardDTO questionBoardDTO);

    void remove(Long questionBoardId);

    List<QuestionBoardDTO> getBoard(int page);

    Long like(Long questionBoardId, QuestionBoardLikeDTO questionBoardLikeDTO);

    List<QuestionBoardDTO> getTopPosts();

    String updateImage(MultipartFile multipartFile) throws IOException;
}
