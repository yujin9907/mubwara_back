package site.metacoding.finals.dto.image_file;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;

public class ImageFileReqDto {

    @Setter
    @Getter
    public static class ImageHandlerDto {
        private Long id;
        private String storeFilename;

        public ImageHandlerDto(String storeFilename) {
            this.storeFilename = storeFilename;
        }

        public ImageFile toReviewEntity(Review review) {
            return ImageFile.builder()
                    .storeFilename(storeFilename)
                    .review(review)
                    .build();
        }

        public ImageFile toShopEntity(Shop shop) {
            return ImageFile.builder()
                    .storeFilename(storeFilename)
                    .shop(shop)
                    .build();
        }

        public ImageFile toMenuEntity(Menu menu) {
            return ImageFile.builder()
                    .storeFilename(storeFilename)
                    .menu(menu)
                    .build();
        }

    }
}
