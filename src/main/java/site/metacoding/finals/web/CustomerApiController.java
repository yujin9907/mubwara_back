package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerUpdateReqDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerJoinRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerMyPageReservationRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerMyPageReviewRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerMyPageSubscribeRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerUpdateRespDto;
import site.metacoding.finals.repositoryDto.customer.ReservationRepositoryRespDto;
import site.metacoding.finals.service.CustomerService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/customer/join")
    public ResponseEntity<?> joinCustomerApi(@RequestBody CustomerJoinReqDto customerJoinReqDto) {
        CustomerJoinRespDto customerJoinRespDto = customerService.join(customerJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "커스터머 회원가입 완료", customerJoinRespDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateCustomerApi(@PathVariable Long id,
            @RequestBody CustomerUpdateReqDto customerUpdateReqDto) {
        // 벨리데이션 체크 나중에

        CustomerUpdateRespDto dto = customerService.update(id, customerUpdateReqDto);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.ACCEPTED, "회원정보수정 완료", dto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer/mypage/reservation/{id}")
    public ResponseEntity<?> CustomerMypageReservationApi(@PathVariable Long id) {
        List<ReservationRepositoryRespDto> dto = customerService.myPageReservation(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 예약 목록", dto), HttpStatus.OK);
    }

    @GetMapping("/customer/mypage/subscribe/{id}")
    public ResponseEntity<?> CustomerMypageSubscribeApi(@PathVariable Long id) {
        List<CustomerMyPageSubscribeRespDto> dto = customerService.myPageSubscribe(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 구독 목록", dto), HttpStatus.OK);
    }

    @GetMapping("/customer/mypage/review/{id}")
    public ResponseEntity<?> CustomerMypageReviewApi(@PathVariable Long id) {
        List<CustomerMyPageReviewRespDto> dto = customerService.myPageReview(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 리뷰 목록", dto), HttpStatus.OK);
    }

}
