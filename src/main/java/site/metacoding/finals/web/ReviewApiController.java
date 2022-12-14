package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyCustomer;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.review.ReviewReqDto.TestReviewReqDto;
import site.metacoding.finals.dto.review.ReviewRespDto.ReviewDataRespDto;
import site.metacoding.finals.dto.review.ReviewRespDto.ReviewSaveRespDto;
import site.metacoding.finals.handler.ImageFileHandler;
import site.metacoding.finals.service.ReviewService;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

        private final ReviewService reviewService;

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

        @VerifyCustomer
        @PostMapping(value = "/auth/review")
        public ResponseEntity<?> saveBase64Review(@AuthenticationPrincipal PrincipalUser principalUser,
                        @RequestBody TestReviewReqDto testReviewReqDto) {
                ReviewSaveRespDto respDto = reviewService.saveBase64(testReviewReqDto,
                                principalUser);
                return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "리뷰 (베이스 64 이미지) 저장", respDto),
                                HttpStatus.CREATED);
        }

        @GetMapping("/list/review")
        public ResponseEntity<?> listReview() {
                List<ReviewDataRespDto> respDto = reviewService.listReview();
                return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "리뷰 리스트 보기", respDto),
                                HttpStatus.OK);
        }

        @GetMapping("/review/{id}")
        public ResponseEntity<?> detailReview(@PathVariable("id") Long reviewId) {
                ReviewDataRespDto respDto = reviewService.detailReview(reviewId);
                return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "리뷰 상세보기", respDto),
                                HttpStatus.OK);
        }

}
