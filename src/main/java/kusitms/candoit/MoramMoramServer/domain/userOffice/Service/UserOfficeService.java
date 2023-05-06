package kusitms.candoit.MoramMoramServer.domain.userOffice.Service;

import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.Authority;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserOfficeService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // Service
    @Transactional
    public ResponseEntity<UserDto.OfficeSaveDto> register(UserDto.OfficeSaveDto request) {
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
                        .marketAdd(request.getMarket_add())
                        .officeAdd(request.getOffice_add())
                        .build()
        );

        Authentication authentication = getAuthentication(request.getEmail(), request.getPassword());
        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return new ResponseEntity<>(UserDto.OfficeSaveDto.response(user, accessToken, refreshToken), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserDto.LoginDto> login(UserDto.LoginDto request) {
        LOGIN_VALIDATE(request);
        Authentication authentication = getAuthentication(request.getEmail(), request.getPassword());
        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return new ResponseEntity<>(UserDto.LoginDto.response(accessToken, refreshToken), HttpStatus.OK);
    }

    // Validate / Method
    private void REGISTER_VALIDATION(UserDto.OfficeSaveDto request) {
/*        if (request.getEmail() == null || request.getPw() == null || request.getName() == null
                || request.getWeight() == null || request.getHeight() == null)
            throw new CustomException(REGISTER_INFO_NULL);*/
        if (request.getMarket_add() == null || request.getOffice_add() == null) {
            throw new CustomException(OFFICE_ADD_OR_MAKET_ADD_IS_REQUIRED);
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

    private Set<Authority> getAuthorities() {
        Authority authority = Authority.builder()
                .authorityName("ROLE_OFFICE")
                .build();
        return Collections.singleton(authority);
    }

    private Authentication getAuthentication(String request, String request1) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request, request1);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
