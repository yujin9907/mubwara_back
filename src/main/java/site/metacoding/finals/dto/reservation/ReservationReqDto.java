package site.metacoding.finals.dto.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.shop_table.ShopTable;

public class ReservationReqDto {

    @Setter
    @Getter
    public static class AnalysisDateReqDto {
        private String date;

        public LocalDate toLocalDate() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(this.date, formatter);
            return date;
        }
    }

    @Getter
    @Setter
    public static class ReservationSelectReqDto {
        private Long shopId;
        private int maxPeople;
        private String date;
    }

    @Setter
    @Getter
    public static class ReservationSaveReqDto {
        private Long shopTableId;
        private String reservationTime;
        private String reservationDate;
        private Long customerId;

        public Reservation toEntity(Customer customer, ShopTable shopTable) {
            return Reservation.builder()
                    .reservationDate(this.reservationDate)
                    .reservationTime(this.reservationTime)
                    .shopTable(shopTable)
                    .customer(customer)
                    .build();
        }
    }
}
