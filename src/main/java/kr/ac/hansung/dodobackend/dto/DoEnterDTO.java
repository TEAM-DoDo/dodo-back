package kr.ac.hansung.dodobackend.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoEnterDTO {
    private Long userId;
    private Long doId;
}
