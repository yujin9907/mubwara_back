package site.metacoding.finals.dto.menu;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.dto.image_file.ImageFileInnerDto.ImageFileDto;
import site.metacoding.finals.dto.shop.ShopInnerDto.ShopDto;

public class MenuReqDto {

    @Setter
    @Getter
    public static class MenuSaveReqDto {
        private String name;
        private Integer price;
        private Integer recommanded;
        private Long shopId;
        private String imageFile;

    }
}
