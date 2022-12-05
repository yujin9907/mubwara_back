package site.metacoding.finals.dto.customer;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.handler.ImageFileHandler;

public class CustomerRespDto {

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
        @JsonIgnore
        private User user;

        public CustomerUpdateRespDto(Customer customer) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.phoneNumber = customer.getPhoneNumber();
            this.address = customer.getAddress();
            this.user = customer.getUser();
        }
    }

    @Getter
    @Setter
    public static class CustomerMyPageReservationRespDto {
        private String reservationTime;
        private String resrevationDate;
        private Shop shop;

        public CustomerMyPageReservationRespDto(Reservation reservation, Shop shop) {
            this.reservationTime = reservation.getReservationTime();
            this.resrevationDate = reservation.getReservationDate();
            this.shop = shop;
        }

        public CustomerMyPageReservationRespDto(List<Reservation> reservation, Shop shop) {
            reservation.stream().map((r) -> new CustomerMyPageReservationRespDto(r, shop)).collect(Collectors.toList());
        }

        // public class ReservationDto {
        // private String reservationTime;
        // private String resrevationDate;

        // public ReservationDto(Reservation reservation) {
        // this.reservationTime = reservation.getReservationTime();
        // this.resrevationDate = reservation.getReservationDate();
        // }

        // } // public class ReservationDto {
        // private String reservationTime;
        // private String resrevationDate;

        // public ReservationDto(Reservation reservation) {
        // this.reservationTime = reservation.getReservationTime();
        // this.resrevationDate = reservation.getReservationDate();
        // }

        // }

        public class ShopDto {
            private String shopName;
            private String category;
            private String address;
            private ImageFile imageFile;

            public ShopDto(Shop shop) {
                shopName = shop.getShopName();
                category = shop.getCategory();
                address = shop.getAddress();
                imageFile = shop.getImageFile();
            }
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
        private String StoreFilename;
        // private String ReservationDate;
        // private String ReservationTime;

        public CustomerMyPageSubscribeRespDto(Shop shop) {
            shopId = shop.getId();
            shopName = shop.getShopName();
            Address = shop.getAddress();
            Category = shop.getCategory();
            StoreFilename = shop.getImageFile().getStoreFilename();
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
        public class ImageFileDto {
            private long id;
            private String image;

            public ImageFileDto(ImageFile imageFile) {
                this.id = imageFile.getId();
                this.image = ImageFileHandler.encodingFile(imageFile.getStoreFilename());
            }

        }

    }

}
