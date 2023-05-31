package kr.ac.hansung.dodobackend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDTO {
    @NotNull(message = "do id is nul")
    private Long doId; //소속된 do id
    @NotBlank(message = "title is blank")
    private String title; //제목
    @NotBlank(message = "startTime is blank")
    private String startTime; //시작시간
    @NotBlank(message = "endTime is blank")
    private String endTime; //종료시간
    @NotBlank(message = "place is blank")
    private String place; //장소
    @NotBlank(message = "cost is blank")
    private String cost;
    private String detail; //상세정보
}
