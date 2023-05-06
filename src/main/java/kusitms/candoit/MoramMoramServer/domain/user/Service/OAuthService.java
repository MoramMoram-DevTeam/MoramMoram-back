package kusitms.candoit.MoramMoramServer.domain.user.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.*;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.Authority;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.OAuth.GoogleOAuth;
import kusitms.candoit.MoramMoramServer.domain.user.OAuth.KakaoOAuth;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.TokenProvider;
import kusitms.candoit.MoramMoramServer.global.config.RedisDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth kakaoOAuth;
    private final GoogleOAuth googleOAuth;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisDao redisDao;
    UsernamePasswordAuthenticationToken authenticationToken = null;
    // validate 및 단순 메소드
    Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

    private ResponseEntity<UserDto.socialLoginResponse> Login(String email, String name, String uimg) {
        if (email.contains("gmail")) {
            authenticationToken = new UsernamePasswordAuthenticationToken(email, "google");
        }
        if (email.contains("daum")) {
            authenticationToken = new UsernamePasswordAuthenticationToken(email, "kakao");
        }

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String atk = tokenProvider.createToken(authentication);
        String rtk = tokenProvider.createRefreshToken(email);

        redisDao.setValues(email, rtk, Duration.ofDays(14));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + atk);

        return new ResponseEntity<>(UserDto.socialLoginResponse.response(
                name, email, uimg, atk, rtk, "SOCIAL_LOGIN_TRUE"
        ), HttpStatus.OK);
    }

    private KakaoUserInfoDto getKakaoUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = kakaoOAuth.requestAccessToken(code);
        KakaoOAuthTokenDto oAuthToken = kakaoOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = kakaoOAuth.requestUserInfo(oAuthToken);
        KakaoUserInfoDto kakaoUser = kakaoOAuth.getUserInfo(userInfoResponse);
        return kakaoUser;
    }

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }

    // Service
    // 구글 로그인 서비스
    @Transactional
    public ResponseEntity<UserDto.socialLoginResponse> googlelogin(String code) throws IOException {
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);
        String email = googleUser.getEmail();
        String name = googleUser.getName();
        // 첫 로그인시 사용자 정보를 보내줌
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(UserDto.socialLoginResponse.response(
                    name, email, null, null, null, "SOCIAL_REGISTER_TRUE"
            ), HttpStatus.OK);
        }
        // 이메일이 존재할시 로그인
        return Login(email, name, null);
    }

    // 카카오 로그인 서비스
    @Transactional
    public ResponseEntity<UserDto.socialLoginResponse> kakaoLogin(String code) throws IOException {
        KakaoUserInfoDto kakaoUser = getKakaoUserInfoDto(code);
        String email = kakaoUser.getKakao_account().getEmail();
        String name = kakaoUser.getProperties().getNickname();
        String profileImagePath = kakaoUser.getProperties().getProfile_image();

        // 첫 로그인시 사용자 정보를 보내줌
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(UserDto.socialLoginResponse.response(
                    name, email, profileImagePath, null, null, "SOCIAL_REGISTER_TRUE"
            ), HttpStatus.OK);
        }
        // 이메일이 존재할시 로그인
        return Login(email, name, profileImagePath);
    }

/*    // 추가 정보 요청 서비스
    @Transactional
    public ResponseEntity<UserDto.registerResponse> socialRegister(UserDto.register request) {
        String social = null;

        if (request.getEmail().contains("gmail")) {
            social = "google";
        }
        if (request.getEmail().contains("daum")) {
            social = "kakao";
        }

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .pw(passwordEncoder.encode(social))
                        .pnum(request.getPnum())
                        .uimg(request.getUimg())
                        .seller(false)
                        .report(0)
                        .marketing(request.getMarketing())
                        .authorities(Collections.singleton(authority))
                        .build()
        );

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), social);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String atk = tokenProvider.createToken(authentication);
        String rtk = tokenProvider.createRefreshToken(request.getEmail());

        redisDao.setValues(request.getEmail(), rtk, Duration.ofDays(14));

        return new ResponseEntity<>(UserDto.registerResponse.response(
                request.getName(),
                request.getEmail(),
                atk,
                rtk
        ), HttpStatus.OK);
    }*/
}
