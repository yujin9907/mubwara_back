package site.metacoding.finals.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.review.ReviewRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.image_file.ImageFileReqDto;
import site.metacoding.finals.dto.image_file.ImageFileReqDto.ImageHandlerDto;
import site.metacoding.finals.dto.review.ReviewReqDto;
import site.metacoding.finals.dto.review.ReviewReqDto.ReviewDetailRepDto;
import site.metacoding.finals.dto.review.ReviewReqDto.TestReviewReqDto;
import site.metacoding.finals.dto.review.ReviewRespDto.ReviewDataRespDto;
import site.metacoding.finals.dto.review.ReviewRespDto.ReviewSaveRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

        private final ShopRepository shopRespository;
        private final CustomerRepository customerRepository;
        private final ReviewRepository reviewRepository;
        private final ImageFileRepository imageFileRepository;
        private final ImageFileHandler imageFileHandler;

        // @Transactional
        // public ReviewSaveRespDto save(List<MultipartFile> multipartFiles,
        // ReviewSaveReqDto dto,
        // PrincipalUser principalUser) {

        // Customer customerPS =
        // customerRepository.findByUserId(principalUser.getUser().getId())
        // .orElseThrow(() -> new RuntimeException("잘못된 유저입니다"));
        // Shop shopPS = shopRespository.findById(dto.getShopId())
        // .orElseThrow(() -> new RuntimeException("잘못된 가게입니다"));

        // List<ImageFile> images = imageFileHandler.storeFile(multipartFiles);
        // for (ImageFile img : images) {
        // imageFileRepository.save(img);
        // }

        // Review review = reviewRepository.save(dto.toEntity(customerPS,
        // shopPS));

        // return new ReviewSaveRespDto(review, images);
        // }

        @Transactional
        public ReviewSaveRespDto saveBase64(TestReviewReqDto dto,
                        PrincipalUser principalUser) {

                Customer customerPS = customerRepository.findByUserId(principalUser.getUser().getId())
                                .orElseThrow(() -> new RuntimeException("잘못된 유저입니다"));
                Shop shopPS = shopRespository.findById(dto.getShopId())
                                .orElseThrow(() -> new RuntimeException("잘못된 가게입니다"));

                Review review = reviewRepository.save(dto.toEntity(customerPS, shopPS));

                List<ImageHandlerDto> images = imageFileHandler.storeFile(dto.getImage());
                for (ImageHandlerDto img : images) {
                        imageFileRepository.save(img.toShopEntity(shopPS));
                }

                return new ReviewSaveRespDto(review);

        }

        public List<ReviewDataRespDto> listReview() {
                List<Review> reviews = reviewRepository.findAll();

                return reviews.stream().map((r) -> new ReviewDataRespDto(r)).collect(Collectors.toList());
        }

        public ReviewDataRespDto detailReview(ReviewDetailRepDto repDto) {
                Review review = reviewRepository.findById(repDto.getId())
                                .orElseThrow(() -> new RuntimeException("찾을 수 없는 리뷰입니다"));

                return new ReviewDataRespDto(review);
        }
}