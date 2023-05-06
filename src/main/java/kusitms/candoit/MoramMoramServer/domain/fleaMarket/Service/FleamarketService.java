package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto.FleamarketDto;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.HostPost;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Like;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.FleamarketRepository;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.HostPostRepository;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository.LikeRepository;
import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import kusitms.candoit.MoramMoramServer.domain.user.Repository.UserRepository;
import kusitms.candoit.MoramMoramServer.global.Exception.CustomException;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.*;
import static kusitms.candoit.MoramMoramServer.global.Model.Status.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FleamarketService {

    private final FleamarketRepository fleamarketRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final HostPostRepository hostPostRepository;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ResponseEntity<List<FleamarketDto.ListDto>> mainPage() {
        List<Fleamarket> fleaMarkets = fleamarketRepository.findAll();
        List<FleamarketDto.ListDto> fleaMarketsDto =
                fleaMarkets.stream().map(FleamarketDto.ListDto::response).toList();

        return new ResponseEntity<>(fleaMarketsDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<FleamarketDto.ListDto>> getMainPageSortedByDeadline() {
        List<Fleamarket> isSortedFleaMarkets =
                fleamarketRepository.findAll(Sort.by(Sort.Direction.ASC, "end")).subList(0, 5);

        List<FleamarketDto.ListDto> sortedFleaMarketsDto =
                isSortedFleaMarkets.stream().map(FleamarketDto.ListDto::response).toList();

        return new ResponseEntity<>(sortedFleaMarketsDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<FleamarketDto.DetailDto> getFleaMarketDetail(Long fleaMarketId) {
        Fleamarket fleaMarket = fleamarketRepository.findById(fleaMarketId).orElseThrow(
                () -> new CustomException(NOT_FOUND_FLEAMARKET)
        );
        String likeCount = likeRepository.countByFleaMarket(fleaMarket).toString();

        fleaMarket.updateViewCount();
        return new ResponseEntity<>(FleamarketDto.DetailDto.response(fleaMarket, likeCount), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Status> toggleFleaMarketLike(FleamarketDto.LikeAddDto request, UserDetails userDetails) {
        User user = getUser(userDetails);
        Fleamarket fleamarket = getFleaMarket(request.getMarketId());
        Optional<Like> myLikeOptional =
                likeRepository.findByFleaMarketAndUser(fleamarket, user);

        // 이미 좋아요를 누른 상태인 경우
        if (myLikeOptional.isPresent()) {
            Like myLike = myLikeOptional.get();
            likeRepository.delete(myLike);
            return new ResponseEntity<>(FLEAMARKET_CANCEL_TRUE, HttpStatus.OK);
        }

        likeRepository.save(
                Like.builder()
                        .fleaMarket(fleamarket)
                        .user(user)
                        .build()
        );

        return new ResponseEntity<>(FLEAMARKET_LIKE_TRUE, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<FleamarketDto.LikeDetailDto>> fetchLikedFleaMarketsByUser(UserDetails userDetails) {
        User user = getUser(userDetails);
        List<Like> myLikes = user.getLikes();
        List<FleamarketDto.LikeDetailDto> myLikesDto =
                myLikes.stream().map(FleamarketDto.LikeDetailDto::response).toList();

        return new ResponseEntity<>(myLikesDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<FleamarketDto.ListDto>> searchFleaMarket(String fleaMarketName) {
        List<Fleamarket> searchedFleaMarkets = fleamarketRepository.findByMarketNameContaining(fleaMarketName);

        List<FleamarketDto.ListDto> searchedFleaMarketsDto =
                searchedFleaMarkets.stream().map(FleamarketDto.ListDto::response).toList();

        return new ResponseEntity<>(searchedFleaMarketsDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Status> createFleaMarketPost(
            FleamarketDto.CreateFleaMarketDto request, MultipartFile multipartFile, UserDetails userDetails
    ) {

        if(multipartFile.isEmpty()){
            throw new CustomException(NEED_TO_FLEAMARKET_IMAGE);
        }

        User user = getUser(userDetails);
        String fleaMarketImageUrl = uploadImage(multipartFile);

        hostPostRepository.save(
                HostPost.builder()
                        .user(user)
                        .fleaMarketName(request.getFleaMarketName())
                        .start(request.getStart())
                        .end(request.getEnd())
                        .deadline(request.getDeadline())
                        .fleaMarketNote(request.getFleaMarketNote())
                        .place(request.getPlace())
                        .category(request.getCategory())
                        .open(request.getOpen())
                        .fleaMarketImage(fleaMarketImageUrl)
                        .build()
        );

        return new ResponseEntity<>(HOST_POST_ADD_TRUE, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<FleamarketDto.HostPostDetailDto>> readFleaMarketPostAll() {
        List<FleamarketDto.HostPostDetailDto> hostPostDetailDtos =
                hostPostRepository.findAll().stream().map(FleamarketDto.HostPostDetailDto::response).toList();
        return new ResponseEntity<>(hostPostDetailDtos, HttpStatus.OK);
    }

    public ResponseEntity<Status> updateFleaMarketPost(Long hostPostId, FleamarketDto.UpdateFleaMarketDto request) {
        HostPost hostPost = getHostPost(hostPostId);
        hostPost.updateHostPost(
                request.getFleaMarketName(), request.getStart(), request.getEnd(), request.getDeadline(),
                request.getFleaMarketNote(), request.getPlace(), request.getCategory(), request.getOpen(),
                request.getFleaMarketNoteImage()
                );

        return new ResponseEntity<>(HOST_POST_EDIT_TRUE, HttpStatus.OK);
    }

    private HostPost getHostPost(Long hostPostId) {
        return hostPostRepository.findById(hostPostId).orElseThrow(
                () -> new CustomException(NOT_FOUND_FLEAMARKET)
        );
    }

    public ResponseEntity<Status> deleteHostPost(Long hostPostId) {
        hostPostRepository.deleteById(hostPostId);
        return new ResponseEntity<>(HOST_POST_DELETE_TRUE, HttpStatus.OK);
    }

    public List<Fleamarket> recommend() {
        return fleamarketRepository.findTop10ByOrderByViewsDesc();
    }

    //이미지 넣기

    public String uploadImage(MultipartFile multipartFile) {
        //이미지 업로드
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID() + toString();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();
        String market_img = "markets/" + now + "/" + fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        try {
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3Client.putObject(bucket, market_img, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new CustomException(FAIL_UPLOAD_IMAGE);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    private User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }

    private Fleamarket getFleaMarket(Long marketId) {
        return fleamarketRepository.findById(marketId).orElseThrow(
                () -> new CustomException(NOT_FOUND_FLEAMARKET)
        );
    }
}
