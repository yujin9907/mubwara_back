package site.metacoding.finals.dto.subscribe;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;

public class SubscribeRespDto {
    @Setter
    @Getter
    public static class SubscribeSaveRespDto {
        private Long id;
        private CustomerDto customer;
        private ShopDto shopDto;

        public SubscribeSaveRespDto(Subscribe subscribe) {
            this.id = subscribe.getId();
            this.customer = new CustomerDto(subscribe.getCustomer());
            this.shopDto = new ShopDto(subscribe.getShop());
        }

        @Getter
        public class CustomerDto {
            private Long id;

            public CustomerDto(Customer customer) {
                this.id = customer.getId();
            }

        }

    }
}
