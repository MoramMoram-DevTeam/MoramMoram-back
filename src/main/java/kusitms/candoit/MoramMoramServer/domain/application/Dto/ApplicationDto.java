package kusitms.candoit.MoramMoramServer.domain.application.Dto;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    private String storeName;
    private String onlineChannel;
    private String returnAddress;

//    private Category category;
//    private SubCategory subCategory;

    private boolean category1;
    private boolean category2;
    private boolean category3;
    private boolean category4;


    private String subCategory1;
    private String subCategory2;
    private String subCategory3;
    private String subCategory4;
    private String subCategory5;

    private Integer marketExp;
    private boolean onlineExp;

//    private Utensil utensil;
    private String certificateImg;

    private String priceAvg;

    private String itemImg;

    private String request;

    public Application toEntity(){

        return Application.builder()
                .storeName(storeName)
                .onlineChannel(onlineChannel)
                .returnAddress(returnAddress)
                .category1(category1)
                .category2(category2)
                .category3(category3)
                .category4(category4)
                .subCategory1(subCategory1)
                .subCategory2(subCategory2)
                .subCategory3(subCategory3)
                .subCategory4(subCategory4)
                .subCategory5(subCategory5)
                .marketExp(marketExp)
                .onlineExp(onlineExp)
                .certificateImg(certificateImg)
                .priceAvg(priceAvg)
                .itemImg(itemImg)
                .request(request)
                .build();
    }

}
