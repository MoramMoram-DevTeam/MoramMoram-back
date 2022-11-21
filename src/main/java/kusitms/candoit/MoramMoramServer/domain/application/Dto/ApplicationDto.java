package kusitms.candoit.MoramMoramServer.domain.application.Dto;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Category;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.SubCategory;
import kusitms.candoit.MoramMoramServer.domain.application.Entity.Utensil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    private String storeName;
    private String onlineChannel;
    private String returnAddress;

    private Category category;
    private SubCategory subCategory;

    private Integer marketExp;
    private boolean onlineExp;

    private Utensil utensil;
    private String certificateImg;

    private String priceAvg;

    private String itemImg;

    private String request;

    public Application toEntity(){

        return Application.builder()
                .storeName(storeName)
                .onlineChannel(onlineChannel)
                .returnAddress(returnAddress)
                .category(category)
                .subCategory(subCategory)
                .marketExp(marketExp)
                .onlineExp(onlineExp)
                .utensil(utensil)
                .certificateImg(certificateImg)
                .priceAvg(priceAvg)
                .itemImg(itemImg)
                .request(request)
                .build();
    }

}
