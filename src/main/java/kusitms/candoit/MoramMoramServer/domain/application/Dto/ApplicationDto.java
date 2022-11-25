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
    private boolean category5;
    private boolean category6;
    private boolean category7;
    private boolean category8;
    private boolean category9;
    private boolean category10;



    private String subCategory1;
    private String subCategory2;
    private String subCategory3;
    private String subCategory4;
    private String subCategory5;

    private Integer marketExp;
    private boolean onlineExp;

    // 가판대
    private boolean stall;
    // 진열대
    private boolean shelf;
    // 조명
    private boolean light;
    // 보자기
    private boolean wrapping;
    // 행거
    private boolean hanger;
    // 마네킹
    private boolean mannequin;
    // 거울
    private boolean mirror;
    // 없음
    private boolean none;

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
                .category5(category5)
                .category6(category6)
                .category7(category7)
                .category8(category8)
                .category9(category9)
                .category10(category10)
                .subCategory1(subCategory1)
                .subCategory2(subCategory2)
                .subCategory3(subCategory3)
                .subCategory4(subCategory4)
                .subCategory5(subCategory5)
                .marketExp(marketExp)
                .onlineExp(onlineExp)
                .stall(stall)
                .shelf(shelf)
                .light(light)
                .wrapping(wrapping)
                .hanger(hanger)
                .mannequin(mannequin)
                .mirror(mirror)
                .none(none)
                .certificateImg(certificateImg)
                .priceAvg(priceAvg)
                .itemImg(itemImg)
                .request(request)
                .build();
    }

}
