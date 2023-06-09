package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.ScheduleEnterDTO;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.service.Impl.ScheduleOfUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-of-user")//토큰 필요
public class ScheduleOfUserController {
    private final ScheduleOfUserServiceImpl scheduleOfUserServiceImpl;

    @PostMapping
    public ResponseEntity<String> CreateScheduleOfUser(@Valid @RequestBody ScheduleEnterDTO scheduleEnterDTO)
    {
        //출력
        System.out.println("클라이언트의 아이디와 참가한 일정의 아이디 :  " + scheduleEnterDTO.getUserId() + ", " + scheduleEnterDTO.getScheduleId());

        //서비스 레이어에게 DTO전달
        scheduleOfUserServiceImpl.CreateScheduleOfUser(scheduleEnterDTO);

        //반환
        return new ResponseEntity<>("저장 성공", HttpStatus.CREATED);
    }
    @GetMapping("/{schedule_id}")
    public ResponseEntity<List<User>> getScheduleOfUser(@PathVariable("schedule_id") long scheduleId)
    {
        //서비스 레이어에게 DTO전달
        var data = scheduleOfUserServiceImpl.getUserOfSchedule(scheduleId);
        //반환
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
    //유저 아이디를 이용해 스케줄 정보 리스트를 반환하는 함수
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Schedule>> getScheduleByUserId(@PathVariable("user_id") long userId){
        List<Schedule> result = scheduleOfUserServiceImpl.getScheduleByUserId(userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
