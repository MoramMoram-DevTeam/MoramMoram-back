package kusitms.candoit.MoramMoramServer.domain.board.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoardLike;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardLikeRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static kusitms.candoit.MoramMoramServer.global.Model.Status.PROFILE_IMAGE_UPLOAD_TRUE;
import static kusitms.candoit.MoramMoramServer.global.Model.Status.QUESTIONS_IMAGE_UPLOAD_TRUE;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class QuestionBoardServiceImpl implements QuestionBoardService {

    private final ModelMapper modelMapper;
    private final QuestionBoardRepository questionBoardRepository;

    private final QuestionBoardLikeRepository questionBoardLikeRepository;

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public Long register(QuestionBoardDTO questionBoardDTO, String img) {
        QuestionBoard board = modelMapper.map(questionBoardDTO, QuestionBoard.class);
        board.setImg(img);
        Long questionBoardId = questionBoardRepository.save(board).getQuestionBoardId();

        return questionBoardId;
    }

    @Override
    public QuestionBoardDTO readOne(Long questionBoardId) {
        log.info("성공2");
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

    //이미지 넣기
    public String updateImage(MultipartFile multipartFile) throws IOException {
        //이미지 업로드
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID()+toString();
        String fileName = uuid+"_"+multipartFile.getOriginalFilename();
        String question_board_image_name = "questions/" + now+"/"+ fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, question_board_image_name, multipartFile.getInputStream(), objMeta);

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
    }


}
