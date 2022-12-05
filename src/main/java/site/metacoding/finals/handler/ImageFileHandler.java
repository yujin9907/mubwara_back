package site.metacoding.finals.handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.review.Review;

@Slf4j
@Component
public class ImageFileHandler {

    @Value("${filedir}") // static 붙이면 못 읽음
    private static String fileDir = "C://temp/image/";

    // 파일 디코딩
    protected byte[] decoder(String base64) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64.getBytes());
    }

    // 파일 이름
    protected String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    // 폴더 생성
    protected void getFolder(String dir) {
        File folder = new File(fileDir);
        if (!folder.exists())
            folder.mkdir();
    }

    // 실제 로직
    public List<ImageFile> storeFile(List<String> files, Review review) {

        log.debug("이미지 핸들러 진입");

        List<byte[]> fileBytes = files.stream().map((f) -> decoder(f)).collect(Collectors.toList());

        getFolder(fileDir);

        List<ImageFile> images = new ArrayList<>();
        String fileName = getUUID() + ".png";

        fileBytes.forEach(fileByte -> {

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileDir + fileName);
                fileOutputStream.write(fileByte);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            images.add(ImageFile.builder()
                    .storeFilename(fileName)
                    .review(review)
                    .build());
        });

        log.debug("이미지 저장 완료");
        return images;
    }

    // 로컬 이미지 인코딩
    public static String encodingFile(String storeName) {
        String storedFile = fileDir + storeName;
        byte[] encodedBytes = null;
        try {
            FileInputStream fis = new FileInputStream(storedFile);
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                byteOutStream.write(buf, 0, len);
            }

            byte[] fileArray = byteOutStream.toByteArray();
            Encoder encoder = Base64.getEncoder();
            encodedBytes = encoder.encode(fileArray);

            fis.close();
            byteOutStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(encodedBytes);

    }

}