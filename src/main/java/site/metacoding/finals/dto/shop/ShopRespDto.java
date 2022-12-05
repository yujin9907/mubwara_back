package site.metacoding.finals.dto.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.feature.Feature;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.handler.ImageFileHandler;

public class ShopRespDto {

    @Setter
    @Getter
    public static class ShopListRespDto {
        private String shopName;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private ImageFileDto imageFileDto;

        public ShopListRespDto(Shop shop) {
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.category = shop.getCategory();
            this.information = shop.getInformation();
            this.openTime = shop.getOpenTime();
            this.closeTime = shop.getCloseTime();
            this.imageFileDto = new ImageFileDto(shop.getImageFile());
        }

        @Getter
        public class ImageFileDto {
            private long id;
            private String image;

            public ImageFileDto(ImageFile imageFile) {
                this.id = imageFile.getId();
                this.image = ImageFileHandler.encodingFile(imageFile.getStoreFilename());
            }

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
    public static class ShopJoinRespDto {
        private User user;

        public ShopJoinRespDto(User user) {
            this.user = user;
        }
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
        private List<Feature> featureList;
        private List<ImageFile> imageFile;
        private User user;

        public ShopInfoSaveRespDto(Shop shop, List<Feature> featureList, List<ImageFile> imageFile) {
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
            this.featureList = featureList;
            this.imageFile = imageFile;
            this.user = shop.getUser();
        }
    }

    @Getter
    @Setter
    public static class ShopDetailRespDto {
        private Shop shop;
        private Feature feature;

        // 예약 가능 시간 정보
        public ShopDetailRespDto(Shop shop, Feature feature) {
            this.shop = shop;
            this.feature = feature;
        }

    }

}
