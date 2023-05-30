package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.ScheduleEnterDTO;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.ScheduleOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.ScheduleOfUserRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.ScheduleOfUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleOfUserServiceImpl implements ScheduleOfUserService {
    private final ScheduleOfUserRepository scheduleOfUserRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
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
    @Override
    public List<User> getUserOfSchedule(long scheduleId){
        var result = scheduleOfUserRepository.findUserListInScheduleByScheduleId(scheduleId);
        return result;
    }

    @Override
    public List<Schedule> getScheduleByUserId(long userId) {
        List<Schedule> result = scheduleOfUserRepository.findScheduleListByUserId(userId);
        return result;
    }

}
