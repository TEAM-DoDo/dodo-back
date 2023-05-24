package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.ScheduleEnterDTO;
import kr.ac.hansung.dodobackend.dto.UserResponseDTO;
import kr.ac.hansung.dodobackend.service.ScheduleOfUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/scheduleOfUser")
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

}
