package site.metacoding.finals.dto.image_file;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.review.Review;

public class ImageFileReqDto {
    @Setter
    @Getter
    public static class ImageSaveReqDto {
        private Long id;
        private String originFilename;
        private String storeFilename;
        private Review review;

    }
}
