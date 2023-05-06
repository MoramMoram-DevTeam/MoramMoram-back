package kusitms.candoit.MoramMoramServer.domain.user.Controller;

import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.OAuth.GoogleOAuth;
import kusitms.candoit.MoramMoramServer.domain.user.OAuth.KakaoOAuth;
import kusitms.candoit.MoramMoramServer.domain.user.Service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;
    private final KakaoOAuth kakaoOAuth;
    private final GoogleOAuth googleOAuth;

    @GetMapping("/kakao")
    public void getKakakoAuthUrl(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoOAuth.responseUrl());
    }

    @GetMapping("login/kakao")
    public ResponseEntity<UserDto.socialLoginResponse> kakaoLogin(
            @RequestParam(name = "code") String code) throws IOException {
        return oAuthService.kakaoLogin(code);
    }

    // 구글 로그인 창 접근
    @GetMapping("google")
    public void getGoogleAuthUrl(HttpServletResponse response) throws Exception {
        response.sendRedirect(googleOAuth.getOauthRedirectURL());
    }

    // 구글 로그인 이후
    @GetMapping("login/google")
    public ResponseEntity<UserDto.socialLoginResponse> callback(
            @RequestParam(name = "code") String code) throws IOException {
        return oAuthService.googlelogin(code);
    }

/*    // Sosial 로그인 이후 추가 정보 요청
    @PostMapping("social/sign-up")
    public ResponseEntity<UserDto.registerResponse> socialRegister(
            @RequestBody final UserDto.register request
    ) {
        return oAuthService.socialRegister(request);
    }*/
}
