package site.metacoding.finals.domain.commercial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ch.qos.logback.core.pattern.color.BoldBlueCompositeConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.finals.domain.AutoTime;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.shop.Shop;

@SQLDelete(sql = "UPDATE commercial SET is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "commercial")
@Getter
public class Commercial extends AutoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specification;

    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    @OneToOne(mappedBy = "commercial") // 레이지 전략 어차피 안 먹음
    private ImageFile imageFile;
    @OneToOne()
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
