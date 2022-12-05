package site.metacoding.finals.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.review.ReviewReqDto.TestReviewReqDto;
import site.metacoding.finals.dto.review.ReviewRespDto.ReviewSaveRespDto;
import site.metacoding.finals.handler.ImageFileHandler;
import site.metacoding.finals.service.ReviewService;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

        private final ReviewService reviewService;
        private final ImageFileHandler imageFileService;

        // 순환 참조
        // @PostMapping(value = "/review", consumes = {
        // MediaType.APPLICATION_JSON_VALUE,
        // MediaType.MULTIPART_FORM_DATA_VALUE })
        // public ResponseEntity<?> saveReview(@RequestPart("file") List<MultipartFile>
        // file,
        // @RequestPart("reqDto") ReviewSaveReqDto reviewSaveReqDto,
        // @AuthenticationPrincipal PrincipalUser principalUser) {
        // ReviewSaveRespDto respDto = reviewService.save(file, reviewSaveReqDto,
        // principalUser);
        // return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "리뷰 저장",
        // respDto),
        // HttpStatus.CREATED);
        // }

        @PostMapping(value = "/test/review")
        public ResponseEntity<?> testBase64Review(@RequestBody TestReviewReqDto testReviewReqDto,
                        @AuthenticationPrincipal PrincipalUser principalUser) {
                ReviewSaveRespDto respDto = reviewService.saveBase64(testReviewReqDto,
                                principalUser);
                return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "베이스64 리뷰 저장 테스트", respDto),
                                HttpStatus.CREATED);
        }

}
