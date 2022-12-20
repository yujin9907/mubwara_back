package site.metacoding.finals.dto.shop;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.dto.image_file.ImageFileReqDto.ImageHandlerDto;
import site.metacoding.finals.dto.repository.shop.PopularListRespDto;
import site.metacoding.finals.dto.repository.shop.ReservationRepositoryRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

public class ShopRespDto {

    @Setter
    @Getter
    public static class ReservationShopRespDto {

        private String shopName;
        private String address;
        private String category;
        private String reservationDate;
        private String reservationTime;
        private ImageFileDto imageFile;

        public ReservationShopRespDto(ReservationRepositoryRespDto dto) {
            this.shopName = dto.getShopName();
            this.address = dto.getAddress();
            this.category = dto.getCategory();
            this.reservationDate = dto.getReservationDate();
            this.reservationTime = dto.getReservationTime();
            this.imageFile = new ImageFileDto(dto.getStoreFilename());
        }

    }

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
            this.imageFileDto = new ImageFileDto(shop.getImageFile().getStoreFilename());
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
        }
    }

    @Setter
    @Getter
    public static class ShopUpdateRespDto {
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
        private ImageFileDto image;

        public ShopUpdateRespDto(Shop shop) {
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
            this.image = new ImageFileDto(shop.getImageFile());
        }

        public ShopUpdateRespDto(Shop shop, String imageName) {
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
            this.image = new ImageFileDto(imageName); // 확인 용도로만
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
        private int perPrice;
        private List<MenuDto> menu;
        private ImageFileDto imageFile;
        private List<ReviewDto> review;
        private Double scoreAvg = 0.0;
        private Long subscribeId = 0L;
        private List<Option> options = new ArrayList<>();

        public ShopDetailRespDto(Shop shop, Subscribe subscribe) {
            this.id = shop.getId();
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.category = shop.getCategory();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.phoneNumber = shop.getPhoneNumber();
            this.perPrice = shop.getPerPrice();
            this.imageFile = new ImageFileDto(shop.getImageFile());
            this.review = toReviewList(shop.getReview());
            this.menu = toMenuList(shop.getMenu());
            this.scoreAvg = 0.0;
            checkSubscribe(subscribe);
        }

        public void checkSubscribe(Subscribe subscribe) {
            if (subscribe != null) {
                this.subscribeId = subscribe.getId();
            }

        }

        public List<MenuDto> toMenuList(List<Menu> menus) {
            return menus.stream().map((m) -> new MenuDto(m)).collect(Collectors.toList());
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
                this.imageFile = new ImageFileDto(menu.getImageFile().getStoreFilename());
            }

        }

    }

    @Setter
    @Getter
    public static class ShopPopularListRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private ImageFileDto imageFileDto;
        private Double scoreAvg = 0.0;

        public ShopPopularListRespDto(PopularListRespDto shop) {
            this.id = shop.getShopId();
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.category = shop.getCategory();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.phoneNumber = shop.getPhoneNumber();
            this.imageFileDto = new ImageFileDto(shop.getStoreFileName());
            this.scoreAvg = shop.getScoreAvg();
        }

    }

    @Setter
    @Getter
    public static class OptionListRespDto {
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

        public OptionListRespDto(BigInteger shopId, String shopName, String address, String category,
                String storeFileName,
                String openTime,
                String closeTime, String phoneNumber, String information, BigDecimal scoreAvg, BigInteger count) {
            this.id = shopId.longValue();
            this.shopName = shopName;
            this.address = address;
            this.category = category;
            this.information = information;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.phoneNumber = phoneNumber;
            this.imageFileDto = new ImageFileDto(storeFileName);
            setAvg(scoreAvg);
        }

        public OptionListRespDto(BigInteger shopId, String shopName, String address, String category,
                String storeFileName,
                String openTime,
                String closeTime, String phoneNumber, String information, Double scoreAvg, BigInteger count) {
            this.id = shopId.longValue();
            this.shopName = shopName;
            this.address = address;
            this.category = category;
            this.information = information;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.phoneNumber = phoneNumber;
            this.imageFileDto = new ImageFileDto(storeFileName);
        }

        public void setAvg(BigDecimal scoreAvg) {
            if (scoreAvg != null) {
                this.scoreAvg = scoreAvg.doubleValue();
            }
            this.scoreAvg = 0.0;
        }

    }

    @Setter
    @Getter
    public static class PriceListRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private ImageFileDto imageFileDto;
        private Double scoreAvg = 0.0;
        private Double count;

        public PriceListRespDto(BigInteger shopId, String shopName, String address, String category,
                String storeFileName,
                String openTime,
                String closeTime, String phoneNumber, BigDecimal count, String information, BigDecimal scoreAvg) {
            this.id = shopId.longValue();
            this.shopName = shopName;
            this.address = address;
            this.category = category;
            this.information = information;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.phoneNumber = phoneNumber;
            this.imageFileDto = new ImageFileDto(storeFileName);
            setAvg(scoreAvg);
            this.count = count.doubleValue();
        }

        public PriceListRespDto(BigInteger shopId, String shopName, String address, String category,
                String storeFileName,
                String openTime,
                String closeTime, String phoneNumber, Double count, String information, Double scoreAvg) {
            this.id = shopId.longValue();
            this.shopName = shopName;
            this.address = address;
            this.category = category;
            this.information = information;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.phoneNumber = phoneNumber;
            this.imageFileDto = new ImageFileDto(storeFileName);
            this.count = count.doubleValue();
        }

        public void setAvg(BigDecimal scoreAvg) {
            if (scoreAvg != null) {
                this.scoreAvg = scoreAvg.doubleValue();
            }
            this.scoreAvg = 0.0;
        }

    }

    @Setter
    @Getter
    public static class ShopSearchListRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private ImageFileDto imageFileDto;
        private Double scoreAvg = 0.0;

        public ShopSearchListRespDto(Shop shop) {
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
    public static class ShopLocationListRespDto {
        private Long id;
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private String phoneNumber;
        private ImageFileDto imageFileDto;
        private Double scoreAvg = 0.0;

        public ShopLocationListRespDto(Shop shop) {
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

}
