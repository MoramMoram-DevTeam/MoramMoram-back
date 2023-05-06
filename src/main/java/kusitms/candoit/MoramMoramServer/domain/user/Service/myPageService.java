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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.*;
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

    // Service
    @Transactional
    public ResponseEntity<Status> delete(UserDto.DeleteDto request, UserDetails userDetails) {
        User user = getUser(userDetails);

        passwordMatched(request.getPassword(), user.getPassword());
        userRepository.delete(user);

        return new ResponseEntity<>(USER_DELETE_STATUS_TRUE, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserDto.DetailDto> read(UserDetails userDetails) {
        User user = getUser(userDetails);
        return new ResponseEntity<>(UserDto.DetailDto.response(user), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Status> updateImage(MultipartFile multipartFile, UserDetails userDetails) {
        User user = getUser(userDetails);
        String profileImageUrl = uploadProfileImage("profile/", user.getEmail(), multipartFile);

        user.updateUserImage(profileImageUrl);

        return new ResponseEntity<>(PROFILE_IMAGE_UPLOAD_TRUE, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Status> licenseUpdate(MultipartFile multipartFile, UserDetails userDetails) {
        User user = getUser(userDetails);
        String uniqueValue = String.valueOf(UUID.randomUUID());
        String businessRegistrationCertificateUrl = uploadProfileImage("license/", uniqueValue, multipartFile);

        user.updateBusinessRegistrationCertificate(businessRegistrationCertificateUrl);;

        return new ResponseEntity<>(LICENSE_UPLOAD_TRUE, HttpStatus.OK);
    }

    // Validate & Method
    private void passwordMatched(String inputPassword, String currentPassword) {
        if (!passwordEncoder.matches(inputPassword, currentPassword)) {
            throw new CustomException(USER_DELETE_STATUS_FALSE);
        }
    }

    private String uploadProfileImage(String type, String uniqueValue, MultipartFile multipartFile) {
        String profileImageName = type + uniqueValue;
        ObjectMetadata objMeta = new ObjectMetadata();
        try {
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3Client.putObject(bucket, profileImageName, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new CustomException(FAIL_UPLOAD_IMAGE);
        }

        return amazonS3Client.getUrl(bucket, profileImageName).toString();
    }

    private User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
