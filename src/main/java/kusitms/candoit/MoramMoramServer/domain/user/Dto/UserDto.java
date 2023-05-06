package kusitms.candoit.MoramMoramServer.domain.user.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserDto implements Serializable {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DeleteDto {
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class OfficeSaveDto {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
        private String userImage;
        private Boolean marketing;
        private String office_add;
        private String market_add;
        private String accessToken;
        private String refreshToken;

        public static OfficeSaveDto response(User user, String accessToken, String refreshToken) {
            return OfficeSaveDto.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Builder
    public static class SaveDto {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
        private String userImage;
        private Boolean marketing;
        private String accessToken;
        private String refreshToken;

        public static SaveDto response(User user, String accessToken, String refreshToken) {
            return SaveDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Builder
    public static class LoginDto {
        private String email;
        private String password;
        private String accessToken;
        private String refreshToken;

        public static LoginDto response(String accessToken, String refreshToken) {
            return LoginDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @Data
    @Builder
    public static class reissue {
        private final String pw;
        private final String email;
        private final String rtk;
    }

    @Data
    @Builder
    public static class socialLoginResponse {
        private final String status;
        private final String name;
        private final String email;
        private final String img;
        private final String atk;
        private final String rtk;

        public static socialLoginResponse response(String name, String email, String img, String atk, String rtk, String status) {
            return socialLoginResponse.builder()
                    .status(status)
                    .name(name)
                    .email(email)
                    .img(img)
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
    }

    @Data
    @Builder
    public static class DetailDto {
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private String userImage;
        private Boolean seller;
        private Integer report;
        private Boolean marketing;
        private String officeAdd;
        private String marketAdd;

        public static DetailDto response(@NotNull User user) {
            return DetailDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .phoneNumber(user.getUserImage())
                    .seller(user.getSeller())
                    .report(user.getReport())
                    .marketing(user.getMarketing())
                    .officeAdd(user.getOfficeAdd())
                    .marketAdd(user.getMarketAdd())
                    .build();
        }
    }
}
