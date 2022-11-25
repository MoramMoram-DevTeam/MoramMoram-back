package kusitms.candoit.MoramMoramServer.domain.board.Entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="question_reply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@ToString(exclude = "question_board")
public class QuestionReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionReplyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="question_board_id")
    private QuestionBoard questionBoard;

    @Column(name = "replyText",columnDefinition = "TEXT", nullable = false)
    private String replyText;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "replyer", nullable = false)
    private String replyer;

    @Column(name = "status", columnDefinition = "varchar(50) default 'ACTIVE'", nullable = false)
    private String status;

}
