package site.metacoding.finals.dto.customer;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopImageDto;

public class CustomerRespDto {

    @Setter
    @Getter
    public static class CustoemrSaveRespDto {
        private String name;
        private String phoneNumber;
        private String address;

        public CustoemrSaveRespDto(Customer customer) {
            this.name = customer.getName();
            this.phoneNumber = customer.getPhoneNumber();
            this.address = customer.getAddress();
        }

    }

    @Setter
    @Getter
    public static class CustomerJoinRespDto {
        private Long id;
        private String name;
        private String phoneNumber;
        private String address;
        private User user;

        public CustomerJoinRespDto(Customer customer, User user) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.phoneNumber = customer.getPhoneNumber();
            this.address = customer.getAddress();
            this.user = user;
        }

    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CustomerUpdateRespDto {
        private Long id;
        private String name;
        private String phoneNumber;
        private String address;

        public CustomerUpdateRespDto(Customer customer) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.phoneNumber = customer.getPhoneNumber();
            this.address = customer.getAddress();
        }
    }

    @Getter
    @Setter
    public static class CustomerMyPageReservationRespDto {
        private String reservationTime;
        private String resrevationDate;
        private ShopImageDto shop;

        public CustomerMyPageReservationRespDto(Reservation reservation, Shop shop) {
            this.reservationTime = reservation.getReservationTime();
            this.resrevationDate = reservation.getReservationDate();
            this.shop = new ShopImageDto(shop);
        }

        public CustomerMyPageReservationRespDto(List<Reservation> reservation, Shop shop) {
            reservation.stream().map((r) -> new CustomerMyPageReservationRespDto(r, shop)).collect(Collectors.toList());
        }

    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class CustomerMyPageSubscribeRespDto {
        private Long shopId;
        private String shopName;
        private String Address;
        private String Category;
        private ImageFileDto imageDto;

        public CustomerMyPageSubscribeRespDto(Shop shop) {
            shopId = shop.getId();
            shopName = shop.getShopName();
            Address = shop.getAddress();
            Category = shop.getCategory();
            imageDto = new ImageFileDto(shop.getImageFile());
            // ReservationDate = reservationDate;
            // ReservationTime = reservationTime;
        }

    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class CustomerMyPageReviewRespDto {
        private int score;
        private String content;
        private List<ImageFileDto> imagefile;
        private ShopDto shop;

        public CustomerMyPageReviewRespDto(Review review) {
            this.score = review.getScore();
            this.content = review.getContent();
            this.imagefile = review.getImageFiles().stream().map((i) -> new ImageFileDto(i))
                    .collect(Collectors.toList());
            this.shop = new ShopDto(review.getShop());
        }

    }

}
