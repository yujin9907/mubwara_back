package site.metacoding.finals.dto.review;

import java.util.List;

import javax.sound.sampled.ReverbType;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;

public class ReviewRespDto {
    @Getter
    @Setter
    public static class ReviewSaveRespDto {
        private Long id;
        private int score;
        private String content;
        private Customer customer;
        private Long shopId;

        public ReviewSaveRespDto(Review review) {
            this.id = review.getId();
            this.score = review.getScore();
            this.content = review.getContent();
            this.customer = review.getCustomer();
            this.shopId = review.getShop().getId();
        }
    }
}