package site.metacoding.finals.dto.shop;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.handler.ImageFileHandler;

public class ShopRespDto {

    @Setter
    @Getter
    public static class ShopListRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private ImageFileDto imageFileDto;
        private Double scoreAvg;

        public ShopListRespDto(Shop shop) {
            this.id = shop.getId();
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.category = shop.getCategory();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.phoneNumber = shop.getPhoneNumber();
            this.imageFileDto = new ImageFileDto(shop.getImageFile());
        }

    }

    @Setter
    @Getter
    public static class ShopReservaitonListRespDto {
        private Shop shop;
        private ImageFile imageFile;
    }

    @Setter
    @Getter
    public static class ShopInfoSaveRespDto {
        private Long id;
        private String shopName;
        private String phoneNumber;
        private String category;
        private String address;
        private String information;
        private String openTime;
        private String closeTime;
        private int perPrice;
        private int perHour;
        private User user;

        public ShopInfoSaveRespDto(Shop shop) {
            this.id = shop.getId();
            this.shopName = shop.getShopName();
            this.phoneNumber = shop.getPhoneNumber();
            this.category = shop.getCategory();
            this.address = shop.getAddress();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.perPrice = shop.getPerPrice();
            this.perHour = shop.getPerHour();
            this.user = shop.getUser();
        }
    }

    @Getter
    @Setter
    public static class ShopDetailRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private MenuDto menu;
        private ImageFileDto imageFile;
        private List<ReviewDto> review;
        private Double scoreAvg;

        public ShopDetailRespDto(Shop shop) {
            this.id = shop.getId();
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.category = shop.getCategory();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.phoneNumber = shop.getPhoneNumber();
            this.imageFile = new ImageFileDto(shop.getImageFile());
            this.review = toReviewList(shop.getReview());
        }

        public List<ReviewDto> toReviewList(List<Review> reviews) {
            return reviews.stream().map((r) -> new ReviewDto(r)).collect(Collectors.toList());
        }

        @Getter
        public class ReviewDto {
            private Long id;
            private int score;
            private String content;

            public ReviewDto(Review review) {
                this.id = review.getId();
                this.score = review.getScore();
                this.content = review.getContent();
            }
        }

        @Getter
        public class MenuDto {
            private String name;
            private Integer price;
            private Integer recommanded; // 5 위까지 지정 제한
            private ImageFileDto imageFile;

            public MenuDto(Menu menu) {
                this.name = menu.getName();
                this.price = menu.getPrice();
                this.recommanded = menu.getRecommanded();
                this.imageFile = new ImageFileDto(menu.getImageFile());
            }

        }

    }

}
