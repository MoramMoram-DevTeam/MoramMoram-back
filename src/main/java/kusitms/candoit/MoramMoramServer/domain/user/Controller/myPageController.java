package kusitms.candoit.MoramMoramServer.domain.user.Controller;

import kusitms.candoit.MoramMoramServer.domain.user.Dto.UserDto;
import kusitms.candoit.MoramMoramServer.domain.user.Service.myPageService;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class myPageController {

    private final myPageService myPageService;

    // 회원탈퇴
    @DeleteMapping("user")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<Status> deleteUser(
            @RequestBody final UserDto.DeleteDto request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return myPageService.delete(request, userDetails);
    }

    // 정보 조회
    @GetMapping("user")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<UserDto.DetailDto> read(
            @AuthenticationPrincipal final UserDetails userDetails
            ) {
        return myPageService.read(userDetails);
    }

    // 프로필 이미지 설정
    @PostMapping("user")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<Status> updateImage(
            @RequestParam("file") MultipartFile multipartFile,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        return myPageService.updateImage(multipartFile, userDetails);
    }

    // 사업자 등록증 설정
    @PostMapping("/certificates/license/new")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<Status> licenseUpdate(
            @RequestParam("file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return myPageService.licenseUpdate(multipartFile, userDetails);
    }


}
