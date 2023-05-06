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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NOT_FOUND_FLEAMARKET;
import static kusitms.candoit.MoramMoramServer.global.Exception.CustomErrorCode.NOT_FOUND_USER;
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

        String likeCount = likeRepository.countByMarketId(fleaMarketId).toString();
        Fleamarket fleaMarket = fleamarketRepository.findById(fleaMarketId).orElseThrow(
                () -> new CustomException(NOT_FOUND_FLEAMARKET)
        );

        fleaMarket.updateViewCount();
        return new ResponseEntity<>(FleamarketDto.DetailDto.response(fleaMarket, likeCount), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Status> toggleFleaMarketLike(FleamarketDto.LikeAddDto request, UserDetails userDetails) {
        User user = getUser(userDetails);
        Optional<Like> myLikeOptional =
                likeRepository.findByMarketIdAndUserId(request.getMarketId(), user.getId());

        // 이미 좋아요를 누른 상태인 경우
        if (myLikeOptional.isPresent()) {
            Like myLike = myLikeOptional.get();
            likeRepository.delete(myLike);
            return new ResponseEntity<>(FLEAMARKET_CANCEL_TRUE, HttpStatus.OK);
        }

        likeRepository.save(
                Like.builder()
                        .marketId(request.getMarketId())
                        .userId(user.getId())
                        .name(user.getName())
                        .build()
        );

        return new ResponseEntity<>(FLEAMARKET_LIKE_TRUE, HttpStatus.OK);
    }

    public ResponseEntity<List<Like>> like_list() {
        Long user_id = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(
                        NullPointerException::new
                ).getId();
        return new ResponseEntity<>(likeRepository.findByUserId(user_id), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<FleamarketDto.ListDto>> searchFleaMarket(String fleaMarketName) {
        List<Fleamarket> searchedFleaMarkets = fleamarketRepository.findByMarketNameContaining(fleaMarketName);

        List<FleamarketDto.ListDto> searchedFleaMarketsDto =
                searchedFleaMarkets.stream().map(FleamarketDto.ListDto::response).toList();

        return new ResponseEntity<>(searchedFleaMarketsDto, HttpStatus.OK);
    }

    public ResponseEntity<Status> hostpost_add(FleamarketDto.hostpost_add request, String img) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(
                NullPointerException::new
        );

        hostPostRepository.save(
                HostPost.builder()
                        .officeId(user.getId())
                        .marketName(request.getMname())
                        .start(request.getStart())
                        .end(request.getEnd())
                        .deadline(request.getDeadline())
                        .mNote(request.getMnote())
                        .place(request.getPlace())
                        .category(request.getCategory())
                        .open(request.getOpen())
                        .mImg(img)
                        .build()
        );


        return new ResponseEntity<>(HOST_POST_ADD_TRUE, HttpStatus.OK);
    }

    public ResponseEntity<List<HostPost>> hostpost_read() {
        return new ResponseEntity<>(hostPostRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Status> hostpost_edit(Long m_id, FleamarketDto.hostpost_edit request) {
        HostPost hostPost = hostPostRepository.findById(m_id).orElseThrow(
                NullPointerException::new
        );

        hostPostRepository.save(
                HostPost.builder()
                        .id(m_id)
                        .officeId(hostPost.getOfficeId())
                        .marketName(request.getMname())
                        .start(request.getStart())
                        .end(request.getEnd())
                        .deadline(request.getDeadline())
                        .mNote(request.getMnote())
                        .place(request.getPlace())
                        .category(request.getCategory())
                        .open(request.getOpen())
                        .mImg(request.getMimg())
                        .createAt(hostPost.getCreateAt())
                        .build()
        );

        return new ResponseEntity<>(HOST_POST_EDIT_TRUE, HttpStatus.OK);
    }

    public ResponseEntity<Status> hostpost_delete(Long m_id) {
        hostPostRepository.deleteById(m_id);
        return new ResponseEntity<>(HOST_POST_DELETE_TRUE, HttpStatus.OK);
    }

    public List<Fleamarket> recommend() {
        return fleamarketRepository.findTop10ByOrderByViewsDesc();
    }

    //이미지 넣기
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        //이미지 업로드
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID() + toString();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();
        String market_img = "markets/" + now + "/" + fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, market_img, multipartFile.getInputStream(), objMeta);

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
    }

    private User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
