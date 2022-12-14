package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerUpdateReqDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerJoinRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerMyPageReviewRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerMyPageSubscribeRespDto;
import site.metacoding.finals.dto.customer.CustomerRespDto.CustomerUpdateRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ReservationShopRespDto;
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

    // 업데이트 권한 체크 필요
    @PutMapping("/user/customer")
    public ResponseEntity<?> updateCustomerApi(@RequestBody CustomerUpdateReqDto customerUpdateReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        // 벨리데이션 체크 나중에

        CustomerUpdateRespDto dto = customerService.update(principalUser, customerUpdateReqDto);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.ACCEPTED, "회원정보수정 완료", dto), HttpStatus.ACCEPTED);
    }

    // 삭제 권한 체크 필요
    @DeleteMapping("/auth/customer")
    public ResponseEntity<?> deleteCustomerApi(@AuthenticationPrincipal PrincipalUser principalUser) {
        customerService.delete(principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.ACCEPTED, "회원 삭제", null), HttpStatus.ACCEPTED);
    }

    // 내거 보기 권한 체크 필요
    @GetMapping("/auth/mypage/reservation")
    public ResponseEntity<?> CustomerMypageReservationApi(
            @AuthenticationPrincipal PrincipalUser principalUser) {
        List<ReservationShopRespDto> dto = customerService.myPageReservation(principalUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 예약 목록", dto), HttpStatus.OK);
    }

    // 내거 보기 권한 체크 필요

    @GetMapping("/auth/mypage/subscribe")
    public ResponseEntity<?> CustomerMypageSubscribeApi(@AuthenticationPrincipal PrincipalUser principalUser) {
        List<CustomerMyPageSubscribeRespDto> dto = customerService.myPageSubscribe(principalUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 구독 목록", dto), HttpStatus.OK);
    }

    // 내거 보기 권한 체크 필요

    @GetMapping("/auth/mypage/review")
    public ResponseEntity<?> CustomerMypageReviewApi(@AuthenticationPrincipal PrincipalUser principalUser) {
        List<CustomerMyPageReviewRespDto> dto = customerService.myPageReview(principalUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "마이페이지 리뷰 목록", dto), HttpStatus.OK);
    }

}
