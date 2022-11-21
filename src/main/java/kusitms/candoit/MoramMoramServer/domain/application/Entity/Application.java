package kusitms.candoit.MoramMoramServer.domain.application.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "application")
@EntityListeners(AuditingEntityListener.class)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @NotNull
    @Column(name = "user_id")
    @Range(min = 1, max = 255)
    private Long userId;

    @NotNull
    @Column(name = "store_name")
    private String storeName;

    @Column(name = "online_channel")
    private String onlineChannel;

    @Column(name = "return_address")
    private String returnAddress;

//    카테고리 (주어진 테이블 내에서 선택)
    @JoinColumn
    @OneToOne
    private Category category;

//    서브 카테고리 (사용자가 직접 입력)
    @JoinColumn
    @OneToOne
    private SubCategory subCategory;

    @NotNull
    @Column(name = "market_exp")
    private Integer marketExp;

    @NotNull
    @Column(name = "online_exp")
    private boolean onlineExp;

//    보유 집기 (주어진 테이블 내에서 선택)
    @JoinColumn
    @OneToOne
    private Utensil utensil;

//    플리마켓 경험 사진 첨부
    @Column(name = "certificate_img", columnDefinition = "TEXT")
    private String certificateImg;

    @NotNull
    @Column(name = "price_avg")
    private String priceAvg;

//    상품 사진 첨부 (여러 개 가능하도록)
    @Column(name = "item_img", columnDefinition = "TEXT")
    private String itemImg;

    @Range(min = 1, max = 255)
    private String request;

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
