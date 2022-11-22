package kusitms.candoit.MoramMoramServer.domain.board.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipBoardLikeDTO {

    private Long userId;

    private String name;

}
