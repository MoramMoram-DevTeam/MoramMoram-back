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
@Table(name = "sub_category")
@EntityListeners(AuditingEntityListener.class)
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Long subCategoryId;

    @NotNull
    @Column(name = "user_id")
    @Size(min = 1, max = 255)
    private Long userId;

    @Column(name="sub_category1")
    private String subCategory1;
    @Column(name="sub_category2")
    private String subCategory2;
    @Column(name="sub_category3")
    private String subCategory3;
    @Column(name="sub_category4")
    private String subCategory4;
    @Column(name="sub_category5")
    private String subCategory5;

}
