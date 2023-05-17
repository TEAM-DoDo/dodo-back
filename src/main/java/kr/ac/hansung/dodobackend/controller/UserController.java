package kr.ac.hansung.dodobackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.model.User;
import kr.ac.hansung.dodobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User GetUser(@PathVariable("id") int id)
    {
        System.out.println("find id : " + id);
        User user = userService.GetUserById(id);
        return user;
    }

    @PostMapping
    public ResponseEntity<?> AddUser(@Valid @RequestBody User user)
    {
        System.out.println("Entered user info : " + user);
        userService.AddUser(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    //Check user 함수가 필요, 만약 check user에서 유저 존재 여부를 확인하면 jwt 토큰을 전송해줘야함
    //로그인 성공시 return은 HttpStatus.OK 및 JWT 토큰 이 경우 바로 홈화면으로 넘어감
    //없을 경우 return은 HttpStatus.BAD_REQUEST이 경우 프론트에서 회원가입으로 넘어감
    //만약 유저가 없다면 회원가입으로 넘어가는 처리 필요
    @PostMapping("/check")
    public ResponseEntity<?> checkUser(@RequestBody String json){
        System.out.println(json);
        Map<String,Object> result;
        try{
            result = new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User u = userService.GetUserByPhone(result.get("phoneNumber").toString());
        System.out.println(json);
        if (u == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //이후 유저의 호출을 받아서 회원가입 진행
}
