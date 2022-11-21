package kusitms.candoit.MoramMoramServer.domain.application.Entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "user_id")
    @Size(min = 1, max = 255)
    private Long userId;

    @Column(columnDefinition = "boolean default false")
    private boolean craft;
    @Column(columnDefinition = "boolean default false")
    private boolean craft2;
    @Column(columnDefinition = "boolean default false")
    private boolean craft3;
    @Column(columnDefinition = "boolean default false")
    private boolean craft4;
    @Column(columnDefinition = "boolean default false")
    private boolean craft5;

}
