package kusitms.candoit.MoramMoramServer.domain.user.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDto implements Serializable {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class delete {
        private String pw;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class officeRegister {
        private Long id;
        private String name;
        private String email;
        private String pw;
        private String pnum;
        private String uimg;
        private Boolean marketing;
        private String office_add;
        private String market_add;
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
        private String atk;
        private String rtk;

        public static SaveDto response(User user, String atk, String rtk) {
            return SaveDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
    }

    @Data
    @Builder
    public static class login {
        private final String pw;
        private final String email;
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
    public static class loginResponse {
        private final String atk;
        private final String rtk;

        public static loginResponse response(String atk, String rtk) {
            return loginResponse.builder()
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
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
    public static class infoResponse {
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

        public static infoResponse response(@NotNull User user) {
            return infoResponse.builder()
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
