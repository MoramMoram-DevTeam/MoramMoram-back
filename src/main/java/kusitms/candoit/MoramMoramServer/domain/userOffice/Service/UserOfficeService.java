package kusitms.candoit.MoramMoramServer.domain.userOffice.Service;

import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.Authority;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.TokenProvider;
import kusitms.candoit.MoramMoramServer.global.config.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserOfficeService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisDao redisDao;

    private void REGISTER_VALIDATION(UserDto.officeRegister request) {
/*        if (request.getEmail() == null || request.getPw() == null || request.getName() == null
                || request.getWeight() == null || request.getHeight() == null)
            throw new CustomException(REGISTER_INFO_NULL);*/
        if(request.getMarket_add() == null || request.getOffice_add() == null){
            throw new CustomException(OFFICE_ADD_OR_MAKET_ADD_IS_REQUIRED);
        }

        if (userRepository.existsByEmail(request.getEmail()))
            throw new CustomException(DUPLICATE_USER);

        if (!request.getEmail().contains("@"))
            throw new CustomException(NOT_EMAIL_FORM);

        if (!(request.getPw().length() > 5))
            throw new CustomException(PASSWORD_SIZE_ERROR);

        if (!(request.getPw().contains("!") || request.getPw().contains("@") || request.getPw().contains("#")
                || request.getPw().contains("$") || request.getPw().contains("%") || request.getPw().contains("^")
                || request.getPw().contains("&") || request.getPw().contains("*") || request.getPw().contains("(")
                || request.getPw().contains(")"))
        ) {
            throw new CustomException(NOT_CONTAINS_EXCLAMATIONMARK);
        }
    }

    private void LOGIN_VALIDATE(UserDto.login request) {
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new CustomException(LOGIN_FALSE)
                );

        if (request.getPw().equals("google"))
            throw new CustomException(NOT_SOCIAL_LOGIN);

        if (request.getPw().equals("kakao"))
            throw new CustomException(NOT_SOCIAL_LOGIN);

        if (!passwordEncoder.matches(
                request.getPw(),
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new CustomException(LOGIN_FALSE)
                        ).getPw())
        ) {
            throw new CustomException(LOGIN_FALSE);
        }
    }

    // Service

    public ResponseEntity<UserDto.registerResponse> register(UserDto.officeRegister request) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_OFFICE")
                .build();

        REGISTER_VALIDATION(request);

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .pw(passwordEncoder.encode(request.getPw()))
                        .pnum(request.getPnum())
                        .uimg(request.getUimg())
                        .seller(false)
                        .report(0)
                        .marketing(request.getMarketing())
                        .authorities(Collections.singleton(authority))
                        .marketAdd(request.getMarket_add())
                        .officeAdd(request.getOffice_add())
                        .build()
        );

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPw());
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
    }

    public ResponseEntity<UserDto.loginResponse> login(UserDto.login request) {
        LOGIN_VALIDATE(request);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String atk = tokenProvider.createToken(authentication);
        String rtk = tokenProvider.createRefreshToken(request.getEmail());

        redisDao.setValues(request.getEmail(), rtk, Duration.ofDays(14));

        return new ResponseEntity<>(UserDto.loginResponse.response(
                atk,
                rtk
        ), HttpStatus.OK);
    }
}
