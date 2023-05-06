package kusitms.candoit.MoramMoramServer.domain.user.Service;

import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.Authority;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.Exception.ServerException;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.TokenProvider;
import kusitms.candoit.MoramMoramServer.global.config.RedisDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.*;
import static kusitms.candoit.MoramMoramServer.global.Model.Status.LOGOUT_TRUE;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisDao redisDao;

    private static Set<Authority> getAuthorities() {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return Collections.singleton(authority);
    }

    // Service
    // 회원가입
    @Transactional
    public ResponseEntity<UserDto.SaveDto> register(UserDto.SaveDto request) {
        REGISTER_VALIDATION(request);

        User user = userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phoneNumber(request.getPhoneNumber())
                        .userImage(request.getUserImage())
                        .marketing(request.getMarketing())
                        .authorities(getAuthorities())
                        .build()
        );

        Authentication authentication = getAuthentication(request.getEmail(), request.getPassword());
        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return new ResponseEntity<>(UserDto.SaveDto.response(user, accessToken, refreshToken), HttpStatus.OK);
    }

    //로그인
    @Transactional
    public ResponseEntity<UserDto.LoginDto> login(UserDto.LoginDto request) {
        LOGIN_VALIDATE(request);

        Authentication authentication = getAuthentication(request.getEmail(), request.getPassword());
        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return new ResponseEntity<>(UserDto.LoginDto.response(accessToken, refreshToken), HttpStatus.OK);
    }

    // accessToken 재발급
    @Transactional
    public ResponseEntity<UserDto.LoginDto> reissue(String refreshToken) {
        String email = tokenProvider.getRefreshTokenInfo(refreshToken);
        String refreshTokenKey = redisDao.getValues(email);
        if (Objects.isNull(refreshTokenKey) || !refreshTokenKey.equals(refreshToken))
            throw new ServerException(REFRESH_TOKEN_IS_BAD_REQUEST); // 410

        String newAccessToken = tokenProvider.reCreateToken(email);

        return new ResponseEntity<>(UserDto.LoginDto.response(newAccessToken, null), HttpStatus.OK);
    }

    // 로그아웃
    @Transactional
    public ResponseEntity<Status> logout(String auth, UserDetails userDetails) {
        String accessToken = auth.substring(7);
        String email = userDetails.getUsername();
        processLogout(accessToken, email);
        return new ResponseEntity<>(LOGOUT_TRUE, HttpStatus.OK);
    }

    // Validate 및 단순화 메소드

    private void LOGIN_VALIDATE(UserDto.LoginDto request) {
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new CustomException(LOGIN_FALSE)
                );

        if (request.getPassword().equals("google"))
            throw new CustomException(NOT_SOCIAL_LOGIN);

        if (request.getPassword().equals("kakao"))
            throw new CustomException(NOT_SOCIAL_LOGIN);

        if (!passwordEncoder.matches(
                request.getPassword(),
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new CustomException(LOGIN_FALSE)
                        ).getPassword())
        ) {
            throw new CustomException(LOGIN_FALSE);
        }
    }

    private void REGISTER_VALIDATION(UserDto.SaveDto request) {
/*        if (request.getEmail() == null || request.getPw() == null || request.getName() == null
                || request.getWeight() == null || request.getHeight() == null)
            throw new CustomException(REGISTER_INFO_NULL);*/
        if (request.getEmail().contains("gmail") || request.getEmail().contains("daum")) {
            throw new CustomException(WANT_SOCIAL_REGISTER);
        }

        if (userRepository.existsByEmail(request.getEmail()))
            throw new CustomException(DUPLICATE_USER);

        if (!request.getEmail().contains("@"))
            throw new CustomException(NOT_EMAIL_FORM);

        if (!(request.getPassword().length() > 5))
            throw new CustomException(PASSWORD_SIZE_ERROR);

        if (!(request.getPassword().contains("!") || request.getPassword().contains("@") || request.getPassword().contains("#")
                || request.getPassword().contains("$") || request.getPassword().contains("%") || request.getPassword().contains("^")
                || request.getPassword().contains("&") || request.getPassword().contains("*") || request.getPassword().contains("(")
                || request.getPassword().contains(")"))
        ) {
            throw new CustomException(NOT_CONTAINS_EXCLAMATIONMARK);
        }
    }

    //셀러인지 아닌지 체크

    public boolean checkSeller(Long userId) {
        log.info("2번");
        Optional<User> result = userRepository.findSellerById(userId);
        User user = result.orElseThrow();

        return user.getSeller();
    }
    private Authentication getAuthentication(String request, String request1) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request, request1);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private void processLogout(String accessToken, String email) {
        if (redisDao.getValues(email) != null) {
            redisDao.deleteValues(email);
        }
        redisDao.setValues(accessToken, "logout",
                Duration.ofMillis(tokenProvider.getExpiration(accessToken)));
    }
}
