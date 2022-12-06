package site.metacoding.finals.util;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.finals.dto.test.jsonObjectMapping;

public class ObjectMapperTest {

    @Test
    public void 제이슨을객체로테스트() {
        //
        String jsonData = "{\"id\":1,\"name\":\"첫번째\",\"innerClass\":{\"id\":2,\"name\":\"두번째\"}, \"another\":\"test\"}";

        ObjectMapper om = new ObjectMapper();

        try {
            jsonObjectMapping result = om.readValue(jsonData, jsonObjectMapping.class);

            System.out.println("디버그 리절트 : " + result.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
