package site.metacoding.finals.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.finals.domain.AutoTime;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.shop.Shop;

@SQLDelete(sql = "UPDATE menu SET is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "menu")
@Entity
public class Menu extends AutoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private Integer price;
    private Integer recommanded; // 5 위까지 지정 제한
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "is_deleted") // 이것도 안 먹음 => 인서트할 때 직접 넣어줌
    private Boolean isDeleted;

    @OneToOne(mappedBy = "menu")
    private ImageFile imageFile;
}
