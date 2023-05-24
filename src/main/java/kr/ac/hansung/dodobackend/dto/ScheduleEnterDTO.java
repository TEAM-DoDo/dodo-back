package kr.ac.hansung.dodobackend.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEnterDTO {
    private Long userId;
    private Long scheduleId;
}
