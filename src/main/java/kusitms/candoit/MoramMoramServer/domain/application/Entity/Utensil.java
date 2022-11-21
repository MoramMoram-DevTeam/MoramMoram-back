package kusitms.candoit.MoramMoramServer.domain.application.Entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "utensil")
@EntityListeners(AuditingEntityListener.class)
public class Utensil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utensil")
    private Long utensilId;

    @NotNull
    @Column(name = "user_id")
    @Size(min = 1, max = 255)
    private Long userId;

    // 가판대
    @Column(columnDefinition = "boolean default false")
    private boolean stall;
    // 진열대
    @Column(columnDefinition = "boolean default false")
    private boolean shelf;
    // 조명
    @Column(columnDefinition = "boolean default false")
    private boolean light;
    // 보자기
    @Column(columnDefinition = "boolean default false")
    private boolean wrapping;
    // 행거
    @Column(columnDefinition = "boolean default false")
    private boolean hanger;
    // 마네킹
    @Column(columnDefinition = "boolean default false")
    private boolean mannequin;
    // 거울
    @Column(columnDefinition = "boolean default false")
    private boolean mirror;
    // 없음
    @Column(columnDefinition = "boolean default false")
    private boolean none;
}
