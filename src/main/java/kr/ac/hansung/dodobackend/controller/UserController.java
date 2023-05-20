package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.SignUpDTO;
import kr.ac.hansung.dodobackend.dto.SignUpResponseDTO;
import kr.ac.hansung.dodobackend.dto.UserResponseDTO;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //Rest api를 위한 컨트롤러. @Controller + @ResponseBody
@RequestMapping(value = "/api/users") //이 컨트롤러의 기본 uri를 설정한다. uri 네이밍은 명사와 복수, 소문자로, 길어질 경우 밑줄(_) 사용 지향
@RequiredArgsConstructor //생성자 주입 지향할 것. @NotNull이 붙은 필드와 final 필드에 대해 의존성 주입
public class UserController { //유저에 대한 컨트롤러
    private final UserServiceImpl userServiceImpl; //생성자가 하나이므로 @Autowired 생략가능
    
    //닉네임으로 유저 조회
    @GetMapping("/{nickname}")
    public ResponseEntity<UserResponseDTO> GetUser(@PathVariable("nickname") String nickname)
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

}
