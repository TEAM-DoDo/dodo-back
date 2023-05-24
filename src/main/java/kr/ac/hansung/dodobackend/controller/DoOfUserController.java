package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.service.DoOfUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/doOfUser")
@RequiredArgsConstructor
public class DoOfUserController {
    private final DoOfUserServiceImpl doOfUserServiceImpl;
    @PostMapping
    public ResponseEntity<String> CreateCommunityOfUser(@Valid @RequestBody DoEnterDTO doEnterDTO)
    {
        //DTO 출력
        System.out.println("클라이언트의 아이디와 입장한 Do 아이디 : " + doEnterDTO.getUserId() + ", " + doEnterDTO.getDoId());

        //서비스 레이어에게 DTO전달
        doOfUserServiceImpl.CreateDoOfUser(doEnterDTO);

        //반환
        return new ResponseEntity<>("조회 성공", HttpStatus.CREATED);
    }
}
