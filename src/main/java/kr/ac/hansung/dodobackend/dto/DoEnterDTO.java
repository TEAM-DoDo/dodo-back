package kr.ac.hansung.dodobackend.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class DoEnterDTO {
    private Long userId;
    private Long doId;
    private boolean isHostTrue;
}
