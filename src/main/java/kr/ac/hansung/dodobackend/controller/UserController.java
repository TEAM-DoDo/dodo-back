package kr.ac.hansung.dodobackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.*;
import kr.ac.hansung.dodobackend.jwt.JwtTokenProvider;
import kr.ac.hansung.dodobackend.service.ImageService;
import kr.ac.hansung.dodobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController //Rest api를 위한 어노테이션. @Controller + @ResponseBody
@RequestMapping(value="/api/users") // 컨트롤러 기본 uri 지정. uri는 명사, 복수, 소문자, 하이픈(-) 사용 지향.
@RequiredArgsConstructor //생성자 주입 지향할 것. @NotNull이 붙은 필드와 final 필드에 대해 의존성 주입
public class UserController {
    //의존성 주입. 생성자가 하나이므로 @Autowired 생략가능
    private final JwtTokenProvider jwtTokenProvider; //생성자 주입
    private final UserServiceImpl userServiceImpl; //생성자 주입
    private final ImageService imageService; //생성자 주입

    //아이디로 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> GetUserByNickname(@PathVariable("id") Long id)
    {
        //조회
        System.out.println("조회하고 싶은 유저의 아이디 : " + id);
        UserResponseDTO userResponseDTO = userServiceImpl.GetUserById(id);

        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //닉네임으로 유저 조회
    @GetMapping("/nickname")
    public ResponseEntity<UserResponseDTO> GetUserByNickname(@RequestParam("nickname") String nickname)
    {
        //조회
        System.out.println("조회하고 싶은 유저의 닉네임 : " + nickname);
        UserResponseDTO userResponseDTO = userServiceImpl.GetUserByNickname(nickname);

        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //유저 신규 생성
    @PostMapping
    public ResponseEntity<SignUpResponseDTO> SignUp(@Valid @RequestBody SignUpDTO signUpDTO) //클라이언트가 날린 정보가 SignUpDTO 인스턴스로 검증 후 매핑됨
    {
        //DTO 출력
        System.out.println("사용자가 입력한 정보 : " + signUpDTO);

        //서비스 레이어에게 DTO 전달
        SignUpResponseDTO signUpResponseDTO = userServiceImpl.SignUp(signUpDTO);
        
        //반환
        return new ResponseEntity<>(signUpResponseDTO, HttpStatus.CREATED);
    }

    //로그인
    @GetMapping
    public ResponseEntity<UserResponseDTO> SignIn(@Valid @RequestBody SignInDTO signInDTO)
    {
        //DTO 출력
        System.out.println("클라이언트로부터 받은 로그인 정보 : " + signInDTO);

        //서비스 레이어에게 DTO전달
        UserResponseDTO userResponseDTO = userServiceImpl.SignIn(signInDTO);
        
        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //프로필 이미지 업데이트
    @PutMapping
    public ResponseEntity<UserResponseDTO> ChangeProfileImage(ProfileImageDTO profileImageDTO)
    {
        //DTO 출력
        System.out.println("클라이언트로부터 받은 유저 아이디와 프로필 이미지 정보 : " + profileImageDTO);

        //서비스 레이어에게 DTO전달
        UserResponseDTO userResponseDTO = userServiceImpl.changeProfileImage(profileImageDTO);

        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //사용자 has 모임
    @GetMapping("/communityList")
    public ResponseEntity<CommunityListOfUserDTO> GetMyCommunityList(Long id)
    {
        //조회
        System.out.println("조회하고 싶은 유저의 아이디 : " + id);
        CommunityListOfUserDTO communityListOfUserDTO = userServiceImpl.GetCommunityListOfUserById(id);

        //반환
        return new ResponseEntity<>(communityListOfUserDTO, HttpStatus.OK);
    }

    //사용자 has 일정
    @GetMapping("/scheduleList")
    public ResponseEntity<ScheduleListOfUserDTO> GetMyScheduleList(Long id)
    {
        //조회
        System.out.println("조회하고 싶은 유저의 아이디 : " + id);
        ScheduleListOfUserDTO scheduleListOfUserDTO = userServiceImpl.GetScheduleListOfUserById(id);

        //반환
        return new ResponseEntity<>(scheduleListOfUserDTO, HttpStatus.OK);
    }

    //Check user 함수가 필요, 만약 check user에서 유저 존재 여부를 확인하면 jwt 토큰을 전송해줘야함
    //로그인 성공시 return은 HttpStatus.OK 및 JWT 토큰 이 경우 바로 홈화면으로 넘어감
    //없을 경우 return은 HttpStatus.BAD_REQUEST이 경우 프론트에서 회원가입으로 넘어감
    //만약 유저가 없다면 회원가입으로 넘어가는 처리 필요
    @PostMapping("/check")
    public ResponseEntity<?> checkUser(@RequestBody String json){
        System.out.println(json);//향후 인증번호를 이곳에서 검증해서 체킹하는 방법도 가능할 듯 하다
//        Map<String,Object> result;
//        try{
//            result = new ObjectMapper().readValue(json, HashMap.class);
//        } catch (JsonProcessingException e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        var d = result.get("phoneNumber").toString();
//        System.out.println(json);
//        if (d == null){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity<>(jwtTokenProvider.createToken(),HttpStatus.OK);
    }
    //이후 유저의 호출을 받아서 회원가입 진행
}
