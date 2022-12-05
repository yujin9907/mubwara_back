package site.metacoding.finals.domain.image_file;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.finals.domain.AutoTime;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;

@Builder
@AllArgsConstructor
@Getter
@Entity
@Table(name = "imagefile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends AutoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeFilename;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @ColumnDefault("0") // 안먹음
    // @JsonBackReference
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @ColumnDefault("0")
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @ColumnDefault("0")
    private Menu menu;

    public void setReview(Review review) {
        this.review = review;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}