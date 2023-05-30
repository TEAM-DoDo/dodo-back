package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.ScheduleEnterDTO;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.User;

import java.util.List;

public interface ScheduleOfUserService {
    void CreateScheduleOfUser(ScheduleEnterDTO scheduleEnterDTO);
    List<User> getUserOfSchedule(long scheduleId);
    List<Schedule> getScheduleByUserId(long userId);
}
