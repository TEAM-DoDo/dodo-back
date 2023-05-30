package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.jwt.JwtTokenProvider;
import kr.ac.hansung.dodobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/refresh")
    public ResponseEntity<?> checkLoginInfo(@RequestBody HashMap<String, Object> param){
        String phoneNumber = param.get("phoneNumber").toString();
        String accessToken = param.get("accessToken").toString();
        String refreshToken = param.get("refreshToken").toString();
        try{
            userService.GetUserByPhoneNumber(phoneNumber);
        }catch (UserNotFoundException e){
            System.out.println("유저가 존재하지 않습니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (jwtTokenProvider.validateToken(accessToken)||jwtTokenProvider.validateToken(refreshToken)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(jwtTokenProvider.createToken(), HttpStatus.OK);
    }
}

