package kusitms.candoit.MoramMoramServer.domain.board.Entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
@Table(name = "tip_board")
public class TipBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_board_id")
    private Long tipBoardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", length=100, nullable = false)
    private String title;

    @Column(name = "note",columnDefinition = "TEXT", nullable = false)
    private String note;

    @Column(name = "img", columnDefinition = "TEXT")
    private String img;

    @Column(name = "view_cnt")
    @ColumnDefault("0")
    private Integer viewCnt;

    @Column(name = "like_cnt")
    @ColumnDefault("0")
    private Integer likeCnt;

    @Column(name = "reply_cnt")
    @ColumnDefault("0")
    private Integer replyCnt;

    @CreatedDate
    @Column(name = "board_date")
    private LocalDateTime boardDate;

    //TODO default 값 지정
    @Column(name = "status", columnDefinition = "varchar(50) default 'ACTIVE'", nullable = false)
    private String status;

    public void changTitle(String title){
        this.title = title;
    }

    public void updateBoardDate(){this.boardDate = LocalDateTime.now();}

    public void changeNote(String note){
        this.note = note;
    }
    public void changeImg(String img){
        this.img = img;
    }

    public void updateLike(){this.likeCnt++;}

    public void updateViewCnt(){this.viewCnt++;}

    public void updateReplyCnt(){this.replyCnt++;}
}

