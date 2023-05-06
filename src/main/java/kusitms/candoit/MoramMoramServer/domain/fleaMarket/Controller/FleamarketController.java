package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Controller;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Dto.FleamarketDto;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.HostPost;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Like;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Service.FleamarketService;
import kusitms.candoit.MoramMoramServer.global.Model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FleamarketController {

    private final FleamarketService fleamarketService;

    @GetMapping("markets")
    public ResponseEntity<List<FleamarketDto.ListDto>> getMainPage() {
        return fleamarketService.mainPage();
    }

    @GetMapping("markets/deadline")
    public ResponseEntity<List<FleamarketDto.ListDto>> getMainPageSortedByDeadline() {
        return fleamarketService.getMainPageSortedByDeadline();
    }

    @GetMapping("markets/")
    public ResponseEntity<FleamarketDto.DetailDto> getFleaMarketDetail(
            @RequestParam Long fleaMarketId
    ) {
        return fleamarketService.getFleaMarketDetail(fleaMarketId);
    }

    @GetMapping("markets/search")
    public ResponseEntity<List<FleamarketDto.ListDto>> searchFleaMarket(
            @RequestParam String fleaMarketName
    ) {
        return fleamarketService.searchFleaMarket(fleaMarketName);
    }

    @PostMapping("wish")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<Status> toggleFleaMarketLike(
            @RequestBody FleamarketDto.LikeAddDto request,
            @AuthenticationPrincipal final UserDetails userDetails
    ) {
        return fleamarketService.toggleFleaMarketLike(request, userDetails);
    }

    @GetMapping("/wish-markets")
    @PreAuthorize("hasAnyRole('ADMIN','USER','OFFICE')")
    public ResponseEntity<List<FleamarketDto.LikeDetailDto>> getLikedFleaMarkets(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return fleamarketService.fetchLikedFleaMarketsByUser(userDetails);
    }

    @PostMapping("markets/register")
    @PreAuthorize("hasAnyRole('OFFICE')")
    public ResponseEntity<Status> createFleaMarketPost(
            @RequestPart(value = "data") FleamarketDto.createFleaMarketDto request,
            @RequestPart(value="file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return fleamarketService.createFleaMarketPost(request, multipartFile, userDetails);
    }

    // 주최 글 조회
    @GetMapping("my-registrations")
    @PreAuthorize("hasAnyRole('ADMIN','OFFICE')")
    public ResponseEntity<List<FleamarketDto.HostPostDetailDto>> readFleaMarketPostAll() {
        return fleamarketService.readFleaMarketPostAll();
    }

    // 주최 글 수정
    @PatchMapping("markets/edit")
    @PreAuthorize("hasAnyRole('ADMIN','OFFICE')")
    public ResponseEntity<Status> hostpost_edit(
            @RequestParam Long m_id,
            @RequestBody FleamarketDto.hostpost_edit request
    ){
        return fleamarketService.hostpost_edit(m_id,request);
    }

    @DeleteMapping("markets/delete")
    @PreAuthorize("hasAnyRole('ADMIN','OFFICE')")
    public ResponseEntity<Status> hostpost_delete(
            @RequestParam Long m_id
    ){
        return fleamarketService.hostpost_delete(m_id);
    }

    @GetMapping("/markets/recommend")
    public List<Fleamarket> recommend(){
        return fleamarketService.recommend();
    }
}
