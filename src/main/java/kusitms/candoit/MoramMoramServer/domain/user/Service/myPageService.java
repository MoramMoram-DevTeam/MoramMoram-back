package kusitms.candoit.MoramMoramServer.domain.user.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NOT_FOUND_USER;
import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.USER_DELETE_STATUS_FALSE;
import static kusitms.candoit.MoramMoramServer.global.Model.Status.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class myPageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    // 회원탈퇴
    @Transactional
    public ResponseEntity<Status> delete(UserDto.DeleteDto request, UserDetails userDetails) {
        User user = getUser(userDetails);

        passwordMatched(request.getPassword(), user.getPassword());
        userRepository.delete(user);

        return new ResponseEntity<>(USER_DELETE_STATUS_TRUE, HttpStatus.OK);
    }

    // 회원 정보 보기
    public ResponseEntity<UserDto.DetailDto> read(UserDetails userDetails) {
        User user = getUser(userDetails);
        return new ResponseEntity<>(UserDto.DetailDto.response(user), HttpStatus.OK);
    }
    private User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }

    public ResponseEntity<Status> updateImage(MultipartFile multipartFile) throws IOException {
        // String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication()
                        .getName()
        ).orElseThrow(
                NullPointerException::new
        );

        String profile_image_name = "profile/" + getTokenInfo().getEmail();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, profile_image_name, multipartFile.getInputStream(), objMeta);

        userRepository.save(
                User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .seller(user.getSeller())
                        .report(user.getReport())
                        .officeAdd(user.getOfficeAdd())
                        .marketAdd(user.getMarketAdd())
                        .marketing(user.getMarketing())
                        .authorities(user.getAuthorities())
                        .userImage(amazonS3Client.getUrl(bucket, profile_image_name).toString())
                        .build()
        );

        return new ResponseEntity<>(PROFILE_IMAGE_UPLOAD_TRUE, HttpStatus.OK);
    }

    public ResponseEntity<Status> licenseUpdate(MultipartFile multipartFile) throws IOException {
        // String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication()
                        .getName()
        ).orElseThrow(
                NullPointerException::new
        );

        UUID uuid = UUID.randomUUID();

        String officeAdd = "license/" + uuid;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, officeAdd, multipartFile.getInputStream(), objMeta);

        userRepository.save(
                User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .seller(user.getSeller())
                        .report(user.getReport())
                        .officeAdd(amazonS3Client.getUrl(bucket, officeAdd).toString())
                        .marketAdd(user.getMarketAdd())
                        .marketing(user.getMarketing())
                        .authorities(user.getAuthorities())
                        .userImage(user.getUserImage())
                        .build()
        );
        return new ResponseEntity<>(LICENSE_UPLOAD_TRUE, HttpStatus.OK);
    }

    private void passwordMatched(String inputPassword, String currentPassword) {
        if (!passwordEncoder.matches(inputPassword, currentPassword)) {
            throw new CustomException(USER_DELETE_STATUS_FALSE);
        }
    }
}
