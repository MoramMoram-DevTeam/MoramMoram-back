package kusitms.candoit.MoramMoramServer.domain.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoogleUserInfoDto {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String picture;
    private String locale;
}
