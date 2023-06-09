package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString(exclude = "scheduleList")
public class ScheduleListOfUserDTO {
    private final User user;
    private final List<Schedule> scheduleList;
}
