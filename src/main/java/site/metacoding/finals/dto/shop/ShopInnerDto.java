package site.metacoding.finals.dto.shop;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;

public class ShopInnerDto {

    @Getter
    @Setter
    public static class ShopImageDto { // CustomerMyPageReservationRespDto
        private Long id;
        private String shopName;
        private String category;
        private String address;
        private ImageFileDto imageFile;

        public ShopImageDto(Shop shop) {
            id = shop.getId();
            shopName = shop.getShopName();
            category = shop.getCategory();
            address = shop.getAddress();
            imageFile = new ImageFileDto(shop.getImageFile());
        }
    }

    @Setter
    @Getter
    public static class ShopDto { // OptionSaveRepsDto
        private Long id;
        private String shopName;

        public ShopDto(Shop shop) {
            this.id = shop.getId();
            this.shopName = shop.getShopName();
        }
    }

    // --------------------------------------------------
    // ShopTableDto // AllShopTableRespDto // SubscribeSaveRespDto
    // //CustomerMyPageReviewRespDto

}
