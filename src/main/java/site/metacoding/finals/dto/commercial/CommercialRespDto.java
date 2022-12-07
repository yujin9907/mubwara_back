package site.metacoding.finals.dto.commercial;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.commercial.Commercial;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.shop.Shop;

public class CommercialRespDto {
    @Setter
    @Getter
    public static class CommercialListRespDto {
        private String Specification;
        private ImageFileDto imageFile;
        private ShopDto shop;

        public CommercialListRespDto(Commercial commercial) {
            this.Specification = commercial.getSpecification();
            this.imageFile = new ImageFileDto(commercial.getImageFile());
            this.shop = new ShopDto(commercial.getShop());
        }

        @Getter
        public class ImageFileDto {
            private String image;

            public ImageFileDto(ImageFile imageFile) {
                this.image = imageFile.getStoreFilename();
            }

        }

        @Getter
        public class ShopDto {
            private Long id;

            public ShopDto(Shop shop) {
                this.id = shop.getId();
            }

        }
    }
}
