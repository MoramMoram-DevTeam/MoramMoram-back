package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity;

import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "fleaMarket")
@EntityListeners(AuditingEntityListener.class)
public class Fleamarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private User user;

    @NotNull
    @Column(name = "m_name")
    private String fleaMarketName;

    @NotNull
    @Column(name = "start")
    private LocalDate start;

    @NotNull
    @Column(name = "end")
    private LocalDate end;

    @NotNull
    private LocalDate deadline;

    @NotNull
    @Column(name = "m_note")
    private String fleaMarketNote;

    @NotNull
    private String place;

    @NotNull
    private String category;

    @NotNull
    private Boolean open;

    private String fleaMarketImage;

    @Builder.Default
    private Integer views = 0;

    public void updateViewCount() {
        this.views += 1;
    }

}
