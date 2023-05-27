package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.DoOfUserRepository;
import kr.ac.hansung.dodobackend.service.DoOfUserService;
import kr.ac.hansung.dodobackend.service.Impl.DoOfUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/do-of-user")
@RequiredArgsConstructor
public class DoOfUserController {
    private final DoOfUserService doOfUserService;
    private final DoOfUserRepository doOfUserRepository;
    @PostMapping
    public ResponseEntity<String> CreateCommunityOfUser(@Valid @RequestBody DoEnterDTO doEnterDTO)
    {
        //DTO 출력
        System.out.println("클라이언트의 아이디와 입장한 Do 아이디 : " + doEnterDTO.getUserId() + ", " + doEnterDTO.getDoId());
        //서비스 레이어에게 DTO전달
        doOfUserService.CreateDoOfUser(doEnterDTO);
        //반환
        doEnterDTO.setHostTrue(false);
        //서비스 레이어에게 DTO전달
        doOfUserService.CreateDoOfUser(doEnterDTO);
        //반환
        return new ResponseEntity<>("조회 성공", HttpStatus.CREATED);
    }

    @GetMapping("/{do_id}")
    public ResponseEntity<List<User>> GetUserListOfDo(@PathVariable("do_id") Long doId)
    {
        List<User> userList = doOfUserRepository.findUserListInDoByDoId(doId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
