package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.ScheduleEnterDTO;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.ScheduleOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.ScheduleOfUserRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleOfUserServiceImpl {
    private final ScheduleOfUserRepository scheduleOfUserRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public void CreateScheduleOfUser(ScheduleEnterDTO scheduleEnterDTO)
    {
        Long userId = scheduleEnterDTO.getUserId();
        Long scheduleId = scheduleEnterDTO.getScheduleId();

        //조회
        Optional<User> user = userRepository.findById(userId);
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);

        //저장
        ScheduleOfUser newScheduleOfUser = ScheduleOfUser.builder().user(user.get()).schedule(schedule.get()).build();
        scheduleOfUserRepository.save(newScheduleOfUser);
    }


}
