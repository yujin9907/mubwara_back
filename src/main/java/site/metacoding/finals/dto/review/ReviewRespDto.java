package site.metacoding.finals.dto.review;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopImageDto;

public class ReviewRespDto {
    @Setter
    @Getter
    public static class ReviewAvgRespDto {
        private Double avg;
    }

    @Getter
    @Setter
    public static class ReviewSaveRespDto {
        private Long id;
        private int score;
        private String content;
        private CustomerDto customer;
        private ShopDto shop;

        public ReviewSaveRespDto(Review review) {
            this.id = review.getId();
            this.score = review.getScore();
            this.content = review.getContent();
            this.customer = new CustomerDto(review.getCustomer());
            this.shop = new ShopDto(review.getShop());
        }

        @Getter
        public class CustomerDto {
            private Long id;

            public CustomerDto(Customer customer) {
                this.id = customer.getId();
            }

        }
    }

    @Setter
    @Getter
    public static class ReviewDataRespDto {
        private Long id;
        private int score;
        private String content;
        private CustomerDto customer;
        private ShopData shop;
        private List<ImageFileDto> images;

        public ReviewDataRespDto(Review review) {
            this.id = review.getId();
            this.score = review.getScore();
            this.content = review.getContent();
            this.customer = new CustomerDto(review.getCustomer());
            this.shop = new ShopData(review.getShop());
            this.images = review.getImageFiles().stream()
                    .map((i) -> new ImageFileDto(i)).collect(Collectors.toList());
        }

        @Getter
        public class CustomerDto {
            private Long id;
            private String name;

            public CustomerDto(Customer customer) {
                this.id = customer.getId();
                this.name = customer.getName();
            }

        }

        @Getter
        public class ShopData extends ShopImageDto {
            private String phoneNumber;

            public ShopData(Shop shop) {
                super(shop);
                this.phoneNumber = shop.getPhoneNumber();
            }

        }
    }
}