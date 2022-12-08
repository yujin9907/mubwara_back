package site.metacoding.finals.dto.menu;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;

public class MenuRespDto {

    @Setter
    @Getter
    public static class MenuSaveRespDto {
        private String name;
        private Integer price;
        private Integer recommanded;
        private ShopDto shop;
        private Long imageFile;

        public MenuSaveRespDto(Menu menu, Long ImageId) {
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.recommanded = menu.getRecommanded();
            this.shop = new ShopDto(menu.getShop());
            this.imageFile = ImageId;
        }

    }

}
