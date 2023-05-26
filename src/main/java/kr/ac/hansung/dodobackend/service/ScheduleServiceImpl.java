package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final DoRepository doRepository;

    public void CreateSchedule(ScheduleDTO scheduleDTO)
    {
        String title = scheduleDTO.getTitle();
        String date = scheduleDTO.getDate();
        String startTime = scheduleDTO.getStartTime();
        String endTime = scheduleDTO.getEndTime();
        String place = scheduleDTO.getPlace();
        String detail = scheduleDTO.getDetail();
        Long doId = scheduleDTO.getDoId();

        //조회
        Optional<Do> currentDo = doRepository.findById(doId);

        //저장
        Schedule schedule = Schedule.builder().date(date).title(title).startTime(startTime).endTime(endTime)
                .place(place).detail(detail).myDo(currentDo.get()).build();
        scheduleRepository.save(schedule);
    }

    public List<Schedule> GetSchedulesByDoId(Do findedDo)
    {
        //조회
        List<Schedule> schedules = scheduleRepository.findByMyDo(findedDo);

        //반환
        return schedules;
    }

}
