package kusitms.candoit.MoramMoramServer.domain.board.Controller;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Service.QuestionReplyService;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final QuestionReplyService questionReplyService;

    private final UserRepository userRepository;

    private final QuestionBoardRepository questionBoardRepository;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }


    @PostMapping(value="/questions/{questionBoardId}/replies", consumes= MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> register(@PathVariable("questionBoardId") Long questionBoardId,
                                       @RequestBody QuestionReplyDTO questionReplyDTO) {

        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();
        questionReplyDTO.setUserId(id);
        questionReplyDTO.setReplyer(name);

        Optional<QuestionBoard> result = questionBoardRepository.findById(questionBoardId);
        QuestionBoard questionBoard = result.orElseThrow();

        questionReplyDTO.setQuestionBoard(questionBoard);

        log.info("일단 여기는 됐고");


        Long questionReplyId = questionReplyService.register(questionReplyDTO);

        return new BaseResponse<>(questionReplyId);
    }

    //댓글 삭제
    @DeleteMapping(value="/questions/replies/{replyId}")
    public BaseResponse<String> remove(
       @PathVariable("replyId") Long replyId) {

        questionReplyService.remove(replyId);

        return new BaseResponse<>("댓글 삭제했습니다.");
    }

//    @GetMapping(
//            value = "/questions/{questionBoardId}/replylist" )
//    public PageResponseDTO<QuestionReplyPagingDTO> getList(@PathVariable("questionBoardId")Long questionBoardId, PageRequestDTO pageRequestDTO) throws Exception {
//
//        PageResponseDTO<QuestionReplyPagingDTO> responseDTO = questionReplyService.getListOfBoard(questionBoardId,
//                pageRequestDTO);
//
//        return responseDTO;
//    }

    @GetMapping(
            value = "/questions/{questionBoardId}/replylist")
    public Object getList(@PathVariable("questionBoardId") Long questionBoardId, @RequestParam(value="page", defaultValue="0") int page) throws Exception {
        log.info("1번");
        return questionReplyService.getReplyList(page, questionBoardId);
    }



}
