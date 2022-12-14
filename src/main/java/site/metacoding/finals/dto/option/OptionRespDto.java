package site.metacoding.finals.dto.option;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option_shop.OptionShop;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;

public class OptionRespDto {

    @Setter
    @Getter
    public static class OptionListRespDto {
        private Long id;
        private String name;
        private ImageFileDto image;

        public OptionListRespDto(Option option) {
            this.id = option.getId();
            this.name = option.getName();
            this.image = new ImageFileDto(option.getImageFile());
        }

    }

    @Setter
    @Getter
    public static class OptionSaveRepsDto {
        private Long id;
        private ShopDto shop;
        private OptionDto option;

        public OptionSaveRepsDto(OptionShop optionShop) {
            this.id = optionShop.getId();
            this.shop = new ShopDto(optionShop.getShop());
            this.option = new OptionDto(optionShop.getOption());
        }

        @Getter
        public class OptionDto {
            private Long id;
            private String name;

            public OptionDto(Option option) {
                this.id = option.getId();
                this.name = option.getName();
            }
        }

    }
}