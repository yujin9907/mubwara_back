package site.metacoding.finals.dto.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;

public class ReservationRespDto {

    @Setter
    @Getter
    public static class AnalysisWeekRespDto {
        private String week;
        private Integer price;

        public AnalysisWeekRespDto(AnalysisDto dtos) {
            this.price = dtos.getResults();
            this.week = toWeek(dtos.getDates());
        }

        public String toWeek(String date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localdate = LocalDate.parse(date, formatter);

            return localdate.getDayOfWeek().toString();

            // 요일별 매출 로직
            // List<AnalysisWeekRespDto> respDtos = new ArrayList<>();
            // int num = 0;
            // while (true) {

            // log.debug("잘 더해지는가" + num);
            // log.debug(date.plusDays(num).getDayOfWeek().toString());

            // AnalysisWeekRespDto dto = new
            // AnalysisWeekRespDto(date.plusDays(num).getDayOfWeek().toString(), null);

            // if (date.plusDays(num).getDayOfWeek().toString().equals("SUNDAY")) {
            // break;
            // }
            // num++;
            // }

        }
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
