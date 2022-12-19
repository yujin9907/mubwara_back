package site.metacoding.finals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.CollectionReadOnlyAccess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.domain.shop_table.ShopTableRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSaveReqDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSelectReqDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.ReservationSaveRespDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.ReservationSelectRespDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.ReservationShopViewAllRespDto;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopRepository shopRespository;
    private final ShopTableRepository shopTableRepository;

    // shop reservation
    public List<ReservationShopViewAllRespDto> viewShopReservation(PrincipalUser principalUser) {

        List<Reservation> reservationPS = reservationRepository.findCustomerByShopId(principalUser.getShop().getId());

        if (reservationPS.size() == 0) {
            new RuntimeApiException("예약이 없음", HttpStatus.NOT_FOUND);
        }

        List<ReservationShopViewAllRespDto> result = new ArrayList<>();
        reservationPS.forEach(r -> {
            result.add(new ReservationShopViewAllRespDto(r));
        });

        return result;
    }

    // customer reservation

    public ReservationSelectRespDto personList(ReservationSelectReqDto dto) {
        List<Integer> tableList = shopTableRepository.findDistinctByShopId(dto.getShopId())
                .orElseThrow(() -> new RuntimeApiException("가게 테이블 없음", HttpStatus.NOT_FOUND));
        return new ReservationSelectRespDto(null, tableList);
    }

    public List<Integer> timeList(ReservationSelectReqDto dto) {
        List<Reservation> reservationList = reservationRepository.findByDataMaxPeople(dto.getMaxPeople(),
                dto.getDate());
        Shop shopPS = shopRespository.findById(dto.getShopId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다"));

        int start = Integer.parseInt(shopPS.getOpenTime());
        int end = Integer.parseInt(shopPS.getCloseTime());

        List<Integer> timeList = new ArrayList<>();

        for (int i = start; i < end; i += shopPS.getPerHour()) {
            timeList.add(i);
            for (Reservation r : reservationList) {
                int disableTime = Integer.parseInt(r.getReservationTime());
                if (disableTime == i) {
                    timeList.remove(timeList.size() - 1);
                }
            }
        }

        List<Integer> distinctTime = timeList.stream().distinct().collect(Collectors.toList());

        return distinctTime;
    }

    public ReservationSaveRespDto save(ReservationSaveReqDto dto, PrincipalUser principalUser) {

        ShopTable shopTablePS = shopTableRepository.findByDataAndTimeAndPeople(dto.getShopId(),
                dto.getReservationDate(), dto.getReservationTime(), dto.getMaxPeople());

        Reservation reservation = reservationRepository.save(dto.toEntity(principalUser.getCustomer(), shopTablePS));

        return new ReservationSaveRespDto(reservation);
    }

}
