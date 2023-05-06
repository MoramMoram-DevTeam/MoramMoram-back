package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.HostPost;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Like;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FleamarketDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ListDto {
        private Long id;
        private Long officeId;
        private String getFleaMarketName;
        private LocalDate start;
        private LocalDate end;
        private LocalDate deadline;
        private String getFleaMarketNote;
        private String place;
        private String category;
        private Boolean open;
        private String getFleaMarketImage;
        private Integer views;
        public static ListDto response(@NotNull Fleamarket fleamarket) {
            return ListDto.builder()
                    .id(fleamarket.getId())
                    .officeId(fleamarket.getUser().getId())
                    .getFleaMarketName(fleamarket.getFleaMarketName())
                    .start(fleamarket.getStart())
                    .end(fleamarket.getEnd())
                    .deadline(fleamarket.getDeadline())
                    .getFleaMarketNote(fleamarket.getFleaMarketNote())
                    .place(fleamarket.getPlace())
                    .category(fleamarket.getCategory())
                    .open(fleamarket.getOpen())
                    .getFleaMarketImage(fleamarket.getFleaMarketImage())
                    .views(fleamarket.getViews())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailDto {
        private Long id;
        private Long officeId;
        private String getFleaMarketName;
        private LocalDate start;
        private LocalDate end;
        private LocalDate deadline;
        private String getFleaMarketNote;
        private String place;
        private String category;
        private Boolean open;
        private String fleaMarketImage;
        private Integer views;
        private String likeCount;
        public static DetailDto response(@NotNull Fleamarket fleamarket, String likeCount) {
            return DetailDto.builder()
                    .id(fleamarket.getId())
                    .officeId(fleamarket.getUser().getId())
                    .getFleaMarketName(fleamarket.getFleaMarketName())
                    .start(fleamarket.getStart())
                    .end(fleamarket.getEnd())
                    .deadline(fleamarket.getDeadline())
                    .getFleaMarketNote(fleamarket.getFleaMarketNote())
                    .place(fleamarket.getPlace())
                    .category(fleamarket.getCategory())
                    .open(fleamarket.getOpen())
                    .fleaMarketImage(fleamarket.getFleaMarketImage())
                    .views(fleamarket.getViews())
                    .likeCount(likeCount)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LikeAddDto{
        private Long marketId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LikeDetailDto{
        private Long id;
        private Long marketName;
        public static LikeDetailDto response(Like like) {
            return LikeDetailDto.builder()
                    .id(like.getId())
                    .marketName(like.getFleaMarket().getId())
                    .build();
        }
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class createFleaMarketDto{
        private Long id;
        private User user;
        private String fleaMarketName;
        private LocalDate start;
        private LocalDate end;
        private LocalDate deadline;
        private String fleaMarketNote;
        private String place;
        private String category;
        private Boolean open;
        private String fleaMarketNoteImage;
        private String count;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class hostpost_edit{
        private Long id;
        private String mname;
        private LocalDate start;
        private LocalDate end;
        private LocalDate deadline;
        private String mnote;
        private String place;
        private String category;
        private Boolean open;
        private String mimg;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HostPostDetailDto{
        private Long id;
        private Long officeId;
        private String fleaMarketName;
        private LocalDate start;
        private LocalDate end;
        private LocalDate deadline;
        private String fleaMarketNote;
        private String place;
        private String category;
        private Boolean open;
        private String fleaMarketImage;

        public static HostPostDetailDto response(HostPost hostPost) {
            return HostPostDetailDto.builder()
                    .id(hostPost.getId())
                    .officeId(hostPost.getUser().getId())
                    .fleaMarketName(hostPost.getFleaMarketName())
                    .start(hostPost.getStart())
                    .end(hostPost.getEnd())
                    .deadline(hostPost.getDeadline())
                    .fleaMarketNote(hostPost.getFleaMarketNote())
                    .place(hostPost.getPlace())
                    .category(hostPost.getCategory())
                    .open(hostPost.getOpen())
                    .fleaMarketImage(hostPost.getFleaMarketImage())
                    .build();
        }
    }
}
