package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Controller;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto.ReviewDto;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Review;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Service.ReviewService;
import kusitms.candoit.MoramMoramServer.global.config.Response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 후기 작성하기
    @PostMapping("/new")
    public BaseResponse<?> newReview(Long m_id, @RequestBody ReviewDto req){
        Review reviewEntity = reviewService.newReview(m_id, req);
        Long reviewId = reviewEntity.getReviewId();
        return new BaseResponse<>(reviewId);
    }

    // 후기 삭제하기
    @DeleteMapping("/delete")
    public BaseResponse<?> deleteReview(Long r_id){
        reviewService.deleteReview(r_id);
        return new BaseResponse<>(r_id);
    }

/*    // 마켓별 후기 리스트
    @GetMapping("/{marketId}")
    public ResponseEntity<?> reviews(@PathVariable Long marketId){
        List<Review> reviews = reviewService.getReviews(marketId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }*/

}
