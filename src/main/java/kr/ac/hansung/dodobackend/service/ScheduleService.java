package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    void CreateSchedule(ScheduleDTO scheduleDTO);
    List<Schedule> GetSchedulesByDoId(Do findedDo);
}
