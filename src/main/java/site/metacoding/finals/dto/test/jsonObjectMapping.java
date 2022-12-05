package site.metacoding.finals.dto.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class jsonObjectMapping {
    private int id;
    private String name;
    private InnerClass innerClass;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class InnerClass {
        private int id;
        private String name;
    }
}
