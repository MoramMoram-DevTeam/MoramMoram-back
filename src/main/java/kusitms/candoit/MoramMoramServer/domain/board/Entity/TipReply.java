package kusitms.candoit.MoramMoramServer.domain.board.Entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="tip_reply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@ToString(exclude = "tip_board")
public class TipReply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipReplyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tip_board_id")
    private TipBoard tipBoard;

    @Column(name = "replyText",columnDefinition = "TEXT", nullable = false)
    private String replyText;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "replyer", nullable = false)
    private String replyer;

    @Column(name = "status", columnDefinition = "varchar(50) default 'ACTIVE'", nullable = false)
    private String status;
}
