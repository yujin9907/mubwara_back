package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSaveReqDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSelectReqDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.ReservationSaveRespDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.ReservationSelectRespDto;
import site.metacoding.finals.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    // @GetMapping("/reservation/date/{shopId}/{date}")

    // @PostMapping("/reservation/date")
    // public ResponseEntity<?> reservationDate(@RequestBody ReservationSelectReqDto
    // dto) {
    // ReservationDateRespDto respDto = reservationService.dateList(dto);
    // return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "예약 가능 날짜 조회",
    // respDto), HttpStatus.OK);
    // }

    @PostMapping("/reservation/person")
    public ResponseEntity<?> reservationPerson(@RequestBody ReservationSelectReqDto dto) {
        ReservationSelectRespDto respDto = reservationService.personList(dto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "예약 가능 인원수 조회", respDto), HttpStatus.OK);
    }

    @PostMapping("/reservation/time")
    public ResponseEntity<?> reservationTime(@RequestBody ReservationSelectReqDto dto) {
        List<Integer> respDto = reservationService.timeList(dto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "예약 가능 시간 조회", respDto), HttpStatus.OK);
    }

    @PostMapping(value = "/reservation")
    public ResponseEntity<?> reservationSave(@RequestBody ReservationSaveReqDto dto,
            @AuthenticationPrincipal UserDetails principalUser) {
        // 이거 왜 디테일유저로 해야 ㅇ릭냐 좆같네 - 테스트에서
        ReservationSaveRespDto respDto = reservationService.save(dto, principalUser.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "예약 저장", respDto), HttpStatus.CREATED);
    }

}
