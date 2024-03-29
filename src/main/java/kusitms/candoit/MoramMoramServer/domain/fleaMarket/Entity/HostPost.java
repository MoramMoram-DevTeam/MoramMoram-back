package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity;

import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "host_post")
@EntityListeners(AuditingEntityListener.class)
public class HostPost {
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

    @NotNull
    private String fleaMarketImage;

    @NotNull
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public void updateHostPost(
            String fleaMarketName, LocalDate start, LocalDate end,
            LocalDate deadline, String fleaMarketNote, String place,
            String category, Boolean open, String fleaMarketImage
    ) {
        this.fleaMarketName = fleaMarketName;
        this.start = start;
        this.end = end;
        this.deadline = deadline;
        this.fleaMarketNote = fleaMarketNote;
        this.place = place;
        this.category = category;
        this.open = open;
        this.fleaMarketImage = fleaMarketImage;
    }
}
