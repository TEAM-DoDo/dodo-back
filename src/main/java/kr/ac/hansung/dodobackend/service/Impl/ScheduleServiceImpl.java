package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoRepository doRepository;

    @Override
    public void CreateSchedule(ScheduleDTO scheduleDTO)
    {
        String title = scheduleDTO.getTitle();
        String startTime = scheduleDTO.getStartTime();
        String endTime = scheduleDTO.getEndTime();
        String place = scheduleDTO.getPlace();
        String detail = scheduleDTO.getDetail();
        String cost = scheduleDTO.getCost();
        Long doId = scheduleDTO.getDoId();

        //조회
        Optional<Do> currentDo = doRepository.findById(doId);

        //저장
        Schedule schedule = Schedule.builder().title(title).startTime(startTime).endTime(endTime)
                .place(place).detail(detail).cost(cost).myDo(currentDo.get()).build();
        scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> GetSchedulesByDoId(Do findedDo)
    {
        //조회
        List<Schedule> schedules = scheduleRepository.findByMyDo(findedDo);

        //반환
        return schedules;
    }
//    public Schedule getRecentSchedule(long doId){
//        Optional<Schedule> schedule = scheduleRepository.getRecentScheduleFromDoId(doId);
//        if (schedule.isPresent()){
//            //에러
//            return null;
//        }
//        return schedule.get();
//    }

}
