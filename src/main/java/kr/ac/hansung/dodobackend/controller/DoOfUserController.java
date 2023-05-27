package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.service.DoOfUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/doOfUser")
@RequiredArgsConstructor
public class DoOfUserController {
    private final DoOfUserService doOfUserService;
    @PostMapping
    public ResponseEntity<String> CreateCommunityOfUser(@Valid @RequestBody DoEnterDTO doEnterDTO)
    {
        //DTO 출력
        System.out.println("클라이언트의 아이디와 입장한 Do 아이디 : " + doEnterDTO.getUserId() + ", " + doEnterDTO.getDoId());

        //서비스 레이어에게 DTO전달
        doOfUserService.CreateDoOfUser(doEnterDTO);

        //반환
        return new ResponseEntity<>("조회 성공", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> GetUserListOfDo(@RequestParam("do_id") Long doId)
    {
        List<User> userList = doOfUserService.getUserListOfDo(doId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
