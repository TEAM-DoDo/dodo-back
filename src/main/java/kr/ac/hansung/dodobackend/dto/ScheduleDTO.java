package kr.ac.hansung.dodobackend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDTO {
    @NotNull(message = "do id is nul")
    private Long doId; //소속된 do id
    @NotBlank(message = "title is blank")
    private String title; //제목
    @NotBlank(message = "date is blank")
    private String date; //날짜
    @NotBlank(message = "startTime is blank")
    private String startTime; //시작시간
    @NotBlank(message = "endTime is blank")
    private String endTime; //종료시간
    @NotBlank(message = "place is blank")
    private String place; //장소
    @NotBlank(message = "detail is blank")
    private String detail; //상세정보
}
