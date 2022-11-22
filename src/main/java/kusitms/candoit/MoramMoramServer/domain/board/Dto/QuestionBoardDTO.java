package kusitms.candoit.MoramMoramServer.domain.board.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBoardDTO {

    private Long questionBoardId;

    private Long userId;

    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String note;

    private String img;

    private Integer viewCnt;

    private Integer likeCnt;

    private Integer replyCnt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    private String status;
}
