package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleServiceImpl;
    private final ScheduleRepository scheduleRepository;

//    @PostMapping
//    public ResponseEntity<String> CreateSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) //스케쥴 전용 dto..
//    {
//        //출력
//        System.out.println("클라이언트가 전송한 일정 개설 정보 : " + scheduleDTO);
//
//        //서비스 레이어에 전달
//        scheduleServiceImpl.CreateSchedule(scheduleDTO);
//
//        return new ResponseEntity<>("새로운 일정 생성 성공", HttpStatus.CREATED);
//    }
}
