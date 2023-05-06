package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Service;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto.ReviewDto;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Review;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.FleamarketRepository;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.ReviewRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Dto.TokenInfoResponseDto;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.config.Jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FleamarketRepository fleamarketRepository;

    private TokenInfoResponseDto getTokenInfo() {
        return TokenInfoResponseDto.Response(
                Objects.requireNonNull(SecurityUtil.getCurrentUsername()
                        .flatMap(
                                userRepository::findOneWithAuthoritiesByEmail)
                        .orElse(null))
        );
    }

    @Transactional
    public Review newReview(Long m_id, ReviewDto reviewDto){

        Fleamarket fleamarket = fleamarketRepository.findById(m_id).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        });

        Review review = reviewDto.toEntity();
        review.setUserId(getTokenInfo().getId());
        review.setFleamarket(fleamarket);

        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long r_id){
        Long userId = getTokenInfo().getId();

        Review review = reviewRepository.findByReviewId(r_id);

        if (review == null){
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }
        if (review.getUserId() == userId){
            reviewRepository.delete(review);
        } else {
            throw new CustomException(CustomErrorCode.NO_AUTHORITY);
        }

    }

    @Transactional
    public List<Review> getReviews(Long m_id){
        Fleamarket fleamarket = fleamarketRepository.findFleamarketById(m_id);
        List<Review> reviews = reviewRepository.findAllByFleamarket(fleamarket);
        if (fleamarket == null){
            throw new CustomException(CustomErrorCode.NOT_FOUND_FLEAMARKET);
        }
        return reviews;
    }

}
