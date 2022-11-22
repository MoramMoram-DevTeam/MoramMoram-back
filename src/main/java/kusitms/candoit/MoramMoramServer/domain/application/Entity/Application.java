package kusitms.candoit.MoramMoramServer.domain.application.Entity;

import kusitms.candoit.MoramMoramServer.domain.category.Entity.SubCategory;
import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @Column(name = "market_id")
    private Long marketId;

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

    //    카테고리
    private Long categoryId;
    @Column(columnDefinition = "boolean default false")
    private boolean category1;
    @Column(columnDefinition = "boolean default false")
    private boolean category2;
    @Column(columnDefinition = "boolean default false")
    private boolean category3;
    @Column(columnDefinition = "boolean default false")
    private boolean category4;


    //    서브 카테고리 (사용자가 직접 입력)
    private Long subCategoryId;
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

    // WAITING: 승인 대기 중, APPROVED: 승인 완료, REJECTED: 승인 거절
    @Column(columnDefinition = "varchar(50) default 'WAITING'")
    private String status;


    @PrePersist // DB에 insert 되기 직전에 실행
    public void created_at(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate  // update 되기 직전 실행
    public void updated_at() { this.updatedAt = LocalDateTime.now(); }

}