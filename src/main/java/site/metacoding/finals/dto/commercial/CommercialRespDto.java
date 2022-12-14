package site.metacoding.finals.dto.commercial;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.commercial.Commercial;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;

public class CommercialRespDto {
    @Setter
    @Getter
    public static class CommercialListRespDto {
        private String Specification;
        private ImageFileDto imageFile;
        private ShopDto shop;

        public CommercialListRespDto(Commercial commercial) {
            this.Specification = commercial.getSpecification();
            this.imageFile = new ImageFileDto(commercial.getImageFile().getStoreFilename());
            this.shop = new ShopDto(commercial.getShop());
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
