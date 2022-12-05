package site.metacoding.finals.dto.review;

import java.util.List;

import javax.sound.sampled.ReverbType;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.image_file.ImageFile;
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
        private ShopDto shopDto;

        public ReviewSaveRespDto(Review review, List<ImageFile> images) {
            this.id = review.getId();
            this.score = review.getScore();
            this.content = review.getContent();
            this.customer = review.getCustomer();
            this.shopDto = new ShopDto(review.getShop());
        }

        @Getter
        public class ShopDto {
            private String shopName;
            private String address;
            private String category;
            private String information;
            private ImageFileDto imageFileDto;

            public ShopDto(Shop shop) {
                this.shopName = shop.getShopName();
                this.address = shop.getAddress();
                this.category = shop.getCategory();
                this.information = shop.getInformation();
                this.imageFileDto = new ImageFileDto(shop.getImageFile());
            }

            @Getter
            public class ImageFileDto {
                private long id;
                private String storeFilename;

                public ImageFileDto(ImageFile imageFile) {
                    this.id = imageFile.getId();
                    this.storeFilename = imageFile.getStoreFilename();
                }

            }
        }
    }
}