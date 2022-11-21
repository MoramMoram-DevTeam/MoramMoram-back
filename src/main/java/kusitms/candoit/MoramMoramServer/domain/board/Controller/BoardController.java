package kusitms.candoit.MoramMoramServer.domain.board.Controller;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Service.QuestionBoardService;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    //TODO 예외처리
    //test
    private final QuestionBoardService questionBoardService;

    private final UserRepository userRepository;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    @GetMapping(
            value = "/questions/list" )
    public Object getList(@RequestParam(value="page", defaultValue="0") int page) throws Exception {
        return questionBoardService.getBoard(page);
    }

    @PostMapping(value = "/questions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<Long> registerPost(@RequestBody@Valid QuestionBoardDTO boardDTO) {

        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();

        boardDTO.setUserId(id);
        boardDTO.setName(name);
        Long questionBoardId = questionBoardService.register(boardDTO);

        return new BaseResponse<>(questionBoardId);
    }

    @GetMapping(
            value = "/questions/{questionBoardId}"
    )
    public BaseResponse<QuestionBoardDTO> getOne(@PathVariable("questionBoardId") Long questionBoardId) throws Exception{
        QuestionBoardDTO questionBoardDTO = questionBoardService.readOne(questionBoardId);
        return new BaseResponse<>(questionBoardDTO);
    }

    @PatchMapping(value = "/questions/{questionBoardId}")
    public BaseResponse<String> modifyOne(@PathVariable("questionBoardId")Long questionBoardId, @RequestBody QuestionBoardDTO questionBoardDTO){
        questionBoardService.modify(questionBoardId,questionBoardDTO);

        return new BaseResponse<>("내용 수정했습니다.");
    }

    @PostMapping(value = "/questions/{questionBoardId}/like")
    public BaseResponse<Long> like(@PathVariable("questionBoardId")Long questionBoardId){
        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();

        QuestionBoardLikeDTO questionBoardLikeDTO = QuestionBoardLikeDTO.builder()
                .userId(id)
                .name(name)
                .build();

        log.info("성공1");
        Long likeId = questionBoardService.like(questionBoardId, questionBoardLikeDTO);
        return new BaseResponse<>(likeId);
    }

    @DeleteMapping(value="/questions/{questionBoardId}")
    public BaseResponse<String> remove(
            @PathVariable("questionBoardId") Long questionBoardId) {

        questionBoardService.remove(questionBoardId);

        return new BaseResponse<>("게시글 삭제했습니다.");
    }

    @GetMapping(
            value = "/questions/top-posts" )
    public Object getTopPosts() throws Exception {
        return questionBoardService.getTopPosts();
    }
}

