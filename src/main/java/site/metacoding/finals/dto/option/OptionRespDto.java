package site.metacoding.finals.dto.option;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option_shop.OptionShop;
import site.metacoding.finals.domain.shop.Shop;

public class OptionRespDto {
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
        public class ShopDto {
            private Long id;
            private String shopName;

            public ShopDto(Shop shop) {
                this.id = shop.getId();
                this.shopName = shop.getShopName();
            }
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