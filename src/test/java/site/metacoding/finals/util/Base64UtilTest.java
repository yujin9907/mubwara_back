
package site.metacoding.finals.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base64UtilTest {
    @Test
    public void 인코딩테스트() {
        String text = "test";
        byte[] targetBytes = text.getBytes();

        // Base64 인코딩 ///////////////////////////////////////////////////
        Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        // Base64 디코딩 ///////////////////////////////////////////////////
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedBytes);

        System.out.println("인코딩 전 : " + text);
        System.out.println("인코딩 text : " + new String(encodedBytes));
        System.out.println("디코딩 text : " + new String(decodedBytes));
    }
}
