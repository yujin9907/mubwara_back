package site.metacoding.finals.dto.reservation;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.shop_table.ShopTable;

public class ReservationRespDto {

    @Setter
    @Getter
    public static class AnalysisWeekRespDto {
        private String week;
        private String price;
    }

    @Setter
    @Getter
    public static class ReservationShopViewAllRespDto {
        private String reservationTime;
        private String reservationDate;
        private CustomerDto customer;
        private ShopTableDto shopTable;

        public ReservationShopViewAllRespDto(Reservation reservation) {
            this.reservationTime = reservation.getReservationTime();
            this.reservationDate = reservation.getReservationDate();
            this.customer = new CustomerDto(reservation.getCustomer());
            this.shopTable = new ShopTableDto(reservation.getShopTable());
        }

        @Getter
        public class CustomerDto {
            private String name;
            private String phoneNumber;

            public CustomerDto(Customer customer) {
                this.name = customer.getName();
                this.phoneNumber = customer.getPhoneNumber();
            }
        }

        @Getter
        public class ShopTableDto {
            private int maxPeople;

            public ShopTableDto(ShopTable shopTable) {
                this.maxPeople = shopTable.getMaxPeople();
            }
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReservationSelectRespDto {
        private List<Integer> date;
        private List<Integer> table;

    }

    @Setter
    @Getter
    public static class ReservationSaveRespDto {
        private Long shopTableId;
        private String reservationTime;
        private String reservationDate;
        private Long customerId;
        private LocalDateTime createdAt;

        public ReservationSaveRespDto(Reservation reservation) {
            this.shopTableId = reservation.getId();
            this.reservationTime = reservation.getReservationTime();
            this.reservationDate = reservation.getReservationDate();
            this.customerId = reservation.getCustomer().getId();
            this.createdAt = reservation.getCreatedAt();
        }

    }

}
