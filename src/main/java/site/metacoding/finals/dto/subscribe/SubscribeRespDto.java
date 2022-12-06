package site.metacoding.finals.dto.subscribe;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.subscribe.Subscribe;

public class SubscribeRespDto {
    @Setter
    @Getter
    public static class SubscribeSaveRespDto {
        private Long id;
        private Customer customer;
        private ShopDto shopDto;

        public SubscribeSaveRespDto(Subscribe subscribe) {
            this.id = subscribe.getId();
            this.customer = subscribe.getCustomer();
            this.shopDto = new ShopDto(subscribe.getShop());
        }

        @Getter
        public class ShopDto {
            private long id;
            private String shopName;

            public ShopDto(Shop shop) {
                this.id = shop.getId();
                this.shopName = shop.getShopName();
            }

        }
    }
}
