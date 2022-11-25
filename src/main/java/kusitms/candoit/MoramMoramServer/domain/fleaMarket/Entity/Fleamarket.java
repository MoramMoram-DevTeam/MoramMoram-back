package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity;

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

    @NotNull
    @Column(name = "office_id")
    private Long officeId;

    @NotNull
    @Column(name = "market_name")
    private String marketName;

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
    private String mNote;

    @NotNull
    private String place;

    @NotNull
    private String category;

    @NotNull
    private Boolean open;

    private String mImg;

    private int views = 0;

}
