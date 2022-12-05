package site.metacoding.finals.dto.reservation;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.reservation.Reservation;

public class ReservationRespDto {

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
