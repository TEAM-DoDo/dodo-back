package kr.ac.hansung.dodobackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.*;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.jwt.JwtTokenProvider;
import kr.ac.hansung.dodobackend.service.AuthService;
import kr.ac.hansung.dodobackend.service.ImageService;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //Rest api를 위한 어노테이션. @Controller + @ResponseBody
@RequestMapping(value="/api/users") // 컨트롤러 기본 uri 지정. uri는 명사, 복수, 소문자, 하이픈(-) 사용 지향.
@RequiredArgsConstructor //생성자 주입 지향할 것. @NotNull이 붙은 필드와 final 필드에 대해 의존성 주입
public class UserController {
    //의존성 주입. 생성자가 하나이므로 @Autowired 생략가능
    private final JwtTokenProvider jwtTokenProvider; //생성자 주입
    private final UserService userService; //생성자 주입
    private final ImageService imageService; //생성자 주입
    private final AuthService authService; //생성자 주입
    private final UserRepository userRepository;

    //인증번호 전송
    @PostMapping("/send-verification")
    public ResponseEntity<?> sendVerificationCode(@RequestBody HashMap<String, Object> param){
        var phoneNum = param.get("phoneNumber").toString();
        System.out.println(phoneNum);
        authService.sendVerification(phoneNum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check-verification")
    public ResponseEntity<LoginDTO> checkVerificationCode(@RequestBody HashMap<String, Object> param){
        var phoneNum = param.get("phoneNumber").toString();
        var certNumber = param.get("certNumber").toString();
        //System.out.println(certNumber);
        if (!authService.checkVerification(phoneNum,certNumber)){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        UserResponseDTO userInfo = null;
        LoginDTO loginDTO = new LoginDTO();
        try {
            userInfo = userService.GetUserByPhoneNumber(phoneNum);
        }catch (UserNotFoundException e){
            System.out.println("해당 전화번호의 사용자가 발견되지 않았습니다. 사용자를 생성합니다");
            User newUser = User.builder().phoneNumber(phoneNum).build();
            User user = userRepository.save(newUser);
            loginDTO.setUserdata(user);
            loginDTO.setTokenInfo(jwtTokenProvider.createToken());
            return new ResponseEntity<LoginDTO>(loginDTO,HttpStatus.CREATED);
        }
        loginDTO.setUserdata(userInfo.getUser());
        loginDTO.setTokenInfo(jwtTokenProvider.createToken());
        return new ResponseEntity<LoginDTO>(loginDTO,HttpStatus.OK);
    }
    @PostMapping("/{user_id}/modify")
    public ResponseEntity<SignUpResponseDTO> modifyUserInfo(@PathVariable("user_id") long userId,@Valid @RequestBody  SignUpDTO signUpDTO){
        SignUpResponseDTO result = userService.modifyUserData(userId,signUpDTO);
        return new ResponseEntity<SignUpResponseDTO>(result,HttpStatus.OK);
    }
    //아이디로 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> GetUserById(@PathVariable("id") Long id) {
        //조회
        UserResponseDTO userResponseDTO = userService.GetUserById(id);

        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //닉네임으로 유저 조회
    @GetMapping("/nickname")
    public ResponseEntity<UserResponseDTO> GetUserByNickname(@RequestParam("nickname") String nickname) {
        //조회
        UserResponseDTO userResponseDTO = userService.GetUserByNickname(nickname);

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
        SignUpResponseDTO signUpResponseDTO = userService.SignUp(signUpDTO);

        //반환
        return new ResponseEntity<>(signUpResponseDTO, HttpStatus.CREATED);
    }

    //로그인
    @GetMapping
    public ResponseEntity<UserResponseDTO> SignIn(@Valid @RequestBody SignInDTO signInDTO) {
        //DTO 출력
        System.out.println("클라이언트로부터 받은 로그인 정보 : " + signInDTO);

        //서비스 레이어에게 DTO전달
        UserResponseDTO userResponseDTO = userService.SignIn(signInDTO);

        //반환
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    //프로필 이미지 업데이트
    @PostMapping("/{user_id}/profile-image")
    public ResponseEntity<?> UploadProfileImage(@PathVariable("user_id") long userId, List<MultipartFile> files)
    {
        if (files.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProfileImageDTO profileImageDTO = new ProfileImageDTO();
        profileImageDTO.setFiles(files);
        profileImageDTO.setId(userId);
        userService.changeProfileImage(profileImageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //프로필 이미지 불러오기
    @GetMapping("/{user_id}/profile-image")
    public ResponseEntity<Resource> DownloadProfileImage(@PathVariable("user_id") long userid)
    {
        var file = userService.getProfileImageByUserId(userid);
        FileSystemResource result = new FileSystemResource(file);
        HttpHeaders header = new HttpHeaders();
        try {
            header.add("Content-Type", Files.probeContentType(file.toPath()));
        } catch (IOException e) {
            System.out.println("Can't add header");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //이미지를 가져오는 알고리즘 작성
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }

    //사용자 has 모임
    @GetMapping("/doList")
    public ResponseEntity<DoListOfUserDTO> GetMyDoList(@RequestParam("id") Long id) {
        //조회
        DoListOfUserDTO doListOfUserDTO = userService.GetDoListOfUserById(id);

        //반환
        return new ResponseEntity<>(doListOfUserDTO, HttpStatus.OK);
    }

    //사용자 has 일정
    @GetMapping("/scheduleList")
    public ResponseEntity<ScheduleListOfUserDTO> GetMyScheduleList(@RequestParam("id") Long id) {
        //조회
        ScheduleListOfUserDTO scheduleListOfUserDTO = userService.GetScheduleListOfUserById(id);

        //반환
        return new ResponseEntity<>(scheduleListOfUserDTO, HttpStatus.OK);
    }

    //Check user 함수가 필요, 만약 check user에서 유저 존재 여부를 확인하면 jwt 토큰을 전송해줘야함
    //로그인 성공시 return은 HttpStatus.OK 및 JWT 토큰 이 경우 바로 홈화면으로 넘어감
    //없을 경우 return은 HttpStatus.BAD_REQUEST이 경우 프론트에서 회원가입으로 넘어감
    //만약 유저가 없다면 회원가입으로 넘어가는 처리 필요
    //테스트용 코드로 변경
    @PostMapping("/check")
    public ResponseEntity<?> checkUser(){
        var token = jwtTokenProvider.createToken();
        System.out.println(token.getAccessToken());
        System.out.println(token.getRefreshToken());
        return new ResponseEntity<>(token,HttpStatus.OK);
    }
    //이후 유저의 호출을 받아서 회원가입 진행
}
