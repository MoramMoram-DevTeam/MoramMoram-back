package kusitms.candoit.MoramMoramServer.domain.board.Controller;


import kusitms.candoit.MoramMoramServer.domain.board.Dto.QuestionReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Dto.TipReplyDTO;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Entity.TipBoard;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.QuestionBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Repository.TipBoardRepository;
import kusitms.candoit.MoramMoramServer.domain.board.Service.QuestionReplyService;
import kusitms.candoit.MoramMoramServer.domain.board.Service.TipReplyService;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TipReplyController {
    private final TipReplyService tipReplyService;

    private final UserRepository userRepository;

    private final TipBoardRepository tipBoardRepository;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }


    @PostMapping(value="/tips/{postId}/replies", consumes= MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> register(@PathVariable("postId") Long postId,
                                       @RequestBody TipReplyDTO tipReplyDTO) {

        Long id = getTokenInfo().getId();
        String name = getTokenInfo().getName();
        tipReplyDTO.setUserId(id);
        tipReplyDTO.setReplyer(name);

        Optional<TipBoard> result = tipBoardRepository.findById(postId);
        TipBoard tipBoard = result.orElseThrow();

        tipReplyDTO.setTipBoard(tipBoard);

        Long tipReplyId = tipReplyService.register(tipReplyDTO);

        return new BaseResponse<>(tipReplyId);
    }

}
