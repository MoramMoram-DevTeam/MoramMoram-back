package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity;

import kusitms.candoit.MoramMoramServer.domain.user.Entity.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "fleamarket_like")
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Long marketId;


    @NotNull
    private String name;
}
