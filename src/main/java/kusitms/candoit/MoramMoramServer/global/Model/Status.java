package kusitms.candoit.MoramMoramServer.global.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    SOCIAL_ADD_INFO_PLZ("추가 정보 요청"),
    SOCIAL_ADD_INFO_STAUTS_TRUE("추가 정보 요청 성공"),
    USER_DELETE_STATUS_TRUE("회원탈퇴 성공"),
    PROFILE_IMAGE_UPLOAD_TRUE("이미지 업로드 성공"),
    LICENSE_UPLOAD_TRUE("사업자 등록증 업로드 성공"),
    FLEAMARKET_CANCEL_TRUE("플리마켓 찜 취소 성공"),
    FLEAMARKET_LIKE_TRUE("플리마켓 찜하기 성공"),
    HOST_POST_ADD_TRUE("주최글 작성 성공"),
    HOST_POST_EDIT_TRUE("주최글 수정 성공"),
    HOST_POST_DELETE_TRUE("주최글 삭제 성공"),
    LOGOUT_TRUE("로그아웃 성공");
    private final String statusMessage;
}
