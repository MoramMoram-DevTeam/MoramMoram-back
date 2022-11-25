package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity;

import kusitms.candoit.MoramMoramServer.domain.board.Entity.QuestionBoard;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id")
    private Fleamarket fleamarket;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist // DB에 insert 되기 직전에 실행
    public void created_at(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate  // update 되기 직전 실행
    public void updated_at() { this.updatedAt = LocalDateTime.now(); }
}
