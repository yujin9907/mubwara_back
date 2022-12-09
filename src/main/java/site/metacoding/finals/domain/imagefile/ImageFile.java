package site.metacoding.finals.domain.imagefile;

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
import site.metacoding.finals.domain.commercial.Commercial;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;

@Builder
@AllArgsConstructor
@Getter
@Entity
@Table(name = "image_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends AutoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeFilename;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commercial_id")
    private Commercial commercial;

    public void setReview(Review review) {
        this.review = review;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}