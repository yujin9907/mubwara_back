package site.metacoding.finals.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;
import site.metacoding.finals.service.SubscribeService;

@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @GetMapping("/user/{shopId}/subscribe")
    public ResponseEntity<?> saveSubscribe(@PathVariable Long shopId,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        SubscribeSaveRespDto respDto = subscribeService.save(shopId, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "구독하기", respDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/user/subscribe/{subscribeId}")
    public ResponseEntity<?> deleteSubscribe(@PathVariable Long subscribeId,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        subscribeService.delete(subscribeId, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.ACCEPTED, "구독취소하기", null), HttpStatus.ACCEPTED);
    }

}
