package site.metacoding.finals.dto.test;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class testDto {
    private Long id;
    private String name;

    public testDto(BigInteger id, String name) {
        this.id = id.longValue();
        this.name = name;
    }

}
