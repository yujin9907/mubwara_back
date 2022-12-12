package site.metacoding.finals.dto.image_file;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.handler.ImageFileHandler;

public class ImageFileInnerDto {

    @Setter
    @Getter
    public static class ImageFileDto { // ShopListRespDto // ShopDetailRespDto //CustomerMyPageReviewRespDto
        private long id;
        private String image;

        public ImageFileDto(ImageFile imageFile) {
            this.id = imageFile.getId();
            this.image = ImageFileHandler.encodingFile(imageFile.getStoreFilename());
        }

        public ImageFileDto(String filename) {
            this.image = ImageFileHandler.encodingFile(filename);
        }

        public ImageFileDto(Long id) {
            this.id = id;
        }

    }
}
