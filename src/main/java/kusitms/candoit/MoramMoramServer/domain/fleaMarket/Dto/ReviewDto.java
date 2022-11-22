package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String content;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .build();
    }

}
