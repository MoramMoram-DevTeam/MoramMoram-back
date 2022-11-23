package kusitms.candoit.MoramMoramServer.domain.category.Entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "board_type")
    private String boardType;

    @NotNull
    @Column(name = "board_id")
    private Long boardId;

    @Column(columnDefinition = "boolean default false")
    private boolean craft1;
    @Column(columnDefinition = "boolean default false")
    private boolean craft2;
    @Column(columnDefinition = "boolean default false")
    private boolean craft3;
    @Column(columnDefinition = "boolean default false")
    private boolean craft4;
    @Column(columnDefinition = "boolean default false")
    private boolean craft5;

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
