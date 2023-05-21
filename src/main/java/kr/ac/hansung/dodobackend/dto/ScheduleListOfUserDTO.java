package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScheduleListOfUserDTO {
    private final User user;
    private final List<Schedule> scheduleList;
}
