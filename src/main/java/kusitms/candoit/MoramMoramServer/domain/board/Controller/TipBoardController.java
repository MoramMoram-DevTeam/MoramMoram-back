package kusitms.candoit.MoramMoramServer.domain.board.Controller;

import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipBoardLikeDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Service.TipBoardService;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Service.UserService;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NOT_SOCIAL_LOGIN;
import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NO_AUTHORITY;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TipBoardController {

    private final TipBoardService tipBoardService;
    private final UserRepository userRepository;

    private final UserService userService;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }
    //생성
    @PostMapping(value = "/tips",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<Long> registerPost(@RequestBody @Valid TipBoardDTO boardDTO) {

        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();
        log.info("1번");
        //seller인지 아닌지 체크
        if(userService.checkSeller(id)==false) {
            log.info("3번");
            throw new CustomException(NO_AUTHORITY);
        }
        log.info("4번");
        boardDTO.setUserId(id);
        boardDTO.setName(name);
        Long questionBoardId = tipBoardService.register(boardDTO);

        return new BaseResponse<>(questionBoardId);
    }

    //게시글 수정
    @PatchMapping(value = "/tips/{postId}")
    public BaseResponse<String> modifyOne(@PathVariable("postId")Long tipBoardId, @RequestBody TipBoardDTO boardDTO){
        tipBoardService.modify( tipBoardId,boardDTO);

        return new BaseResponse<>("내용 수정했습니다.");
    }

    //게시글 삭제 : status를 deleted로
    @PatchMapping(value = "/tips/{postId}/status/deleted")
    public BaseResponse<String> deleteOne(@PathVariable("postId")Long tipBoardId ){
        tipBoardService.deleteOne(tipBoardId);

        return new BaseResponse<>("삭제했습니다.");
    }

    //글 상세 보기
    @GetMapping(
            value = "/tips/{postId}"
    )
    public BaseResponse<TipBoardDTO> getOne(@PathVariable("postId") Long tipBoardId) throws Exception{
        TipBoardDTO tipBoard = tipBoardService.readOne(tipBoardId);
        return new BaseResponse<>(tipBoard);
    }

    @GetMapping(
            value = "/tips/list")
    public BaseResponse<Object> getList(@RequestParam(value="page", defaultValue="0") int page) throws Exception {
        Object tipBoardLists = tipBoardService.getBoard(page);

        return new BaseResponse<>(tipBoardLists);
    }

    //좋아요
    @PostMapping(value = "/tips/{postId}/like")
    public BaseResponse<Long> like(@PathVariable("postId")Long postId){
        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();

        TipBoardLikeDTO tipBoardLikeDTO = TipBoardLikeDTO.builder()
                .userId(id)
                .name(name)
                .build();

        log.info("성공1");
        Long likeId = tipBoardService.like(postId, tipBoardLikeDTO);
        return new BaseResponse<>(likeId);
    }
}
