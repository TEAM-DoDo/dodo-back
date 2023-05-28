package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.*;
import kr.ac.hansung.dodobackend.entity.*;
import kr.ac.hansung.dodobackend.exception.FileNotFoundException;
import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.repository.DoOfUserRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleOfUserRepository;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 주입
public class UserServiceImpl implements UserService { //유저 서비스 레이어
    private final UserRepository userRepository; //생성자 주입
    private final ImageServiceImpl imageServiceImpl; //생성자 주입
    private final DoOfUserRepository doOfUserRepository; //생성자 주입
    private final ScheduleOfUserRepository scheduleOfUserRepository; //생성자 주입
    @Override
    public UserResponseDTO GetUserById(Long id) {
        //아이디로 조회
        Optional<User> user = userRepository.findById(id);

        //만약 아이디에 해당하는 유저가 없다면 에러처리
        if(user.isPresent() == false)
        {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(user.get()).build();
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO GetUserByNickname(String nickname) {
        //조회
        User user = userRepository.findByNickname(nickname);

        //만약 아이디에 해당하는 유저가 없다면 에러처리
        if(user == null)
        {
            System.out.println("닉네임 조회 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(user).build();
        return userResponseDTO;
    }
    @Override
    public SignUpResponseDTO modifyUserData(long id,SignUpDTO signUpDTO){
        User modifiedUser = User.builder().id(id).phoneNumber(signUpDTO.getPhoneNumber()).gender(signUpDTO.getGender()).nickname(signUpDTO.getNickname()).dateOfBirth(signUpDTO.getDateOfBirth())
                .address(signUpDTO.getAddress()).category(signUpDTO.getCategory()).build();
        User user = userRepository.save(modifiedUser);
        SignUpResponseDTO signUpResponseDTO = SignUpResponseDTO.builder().phoneNumber(user.getPhoneNumber()).nickname(user.getNickname())
                .dateOfBirth(user.getDateOfBirth()).address(user.getAddress()).gender(user.getGender())
                .category(user.getCategory()).statusForTest("새로운 유저 가입 완료!").build();
        return signUpResponseDTO;
    }
    @Override
    public SignUpResponseDTO SignUp(SignUpDTO signUpDTO) {
        //DTO를 Entity로 변환
        User newUser = User.builder().phoneNumber(signUpDTO.getPhoneNumber()).nickname(signUpDTO.getNickname())
                .dateOfBirth(signUpDTO.getDateOfBirth()).address(signUpDTO.getAddress())
                .gender(signUpDTO.getGender()).category(signUpDTO.getCategory()).level(1)
                .profileImagePath("").build();

        //Entity 저장
        userRepository.save(newUser);

        //검색
        User user = userRepository.findByNickname(signUpDTO.getNickname());

        //반환
        SignUpResponseDTO signUpResponseDTO = SignUpResponseDTO.builder().phoneNumber(user.getPhoneNumber()).nickname(user.getNickname())
                .dateOfBirth(user.getDateOfBirth()).address(user.getAddress()).gender(user.getGender())
                .category(user.getCategory()).statusForTest("새로운 유저 가입 완료!").build();
        return signUpResponseDTO;
    }

    @Override
    public UserResponseDTO SignIn(SignInDTO signInDTO) {
        //조회
        String phoneNumber = signInDTO.getPhoneNumber();
        UserResponseDTO userResponseDTO = GetUserByPhoneNumber(phoneNumber);
        
        //반환
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO GetUserByPhoneNumber(String phoneNumber) {
        //조회
        User user = userRepository.findByPhoneNumber(phoneNumber);

        //만약 아이디에 해당하는 유저가 없다면 에러처리
        if(user == null)
        {
            System.out.println("핸드폰번호 조회 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(user).build();
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO changeProfileImage(ProfileImageDTO profileImageDTO) {
        //유저 조회
        Optional<User> user = userRepository.findById(profileImageDTO.getId());
        if(user.isPresent() == false)
        {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //이미지 저장
        List<MultipartFile> files = profileImageDTO.getFiles();
        String imageSaveFolderName = "/users/";
        String savedProfileImagePath = imageServiceImpl.putFile(imageSaveFolderName, files.get(0), files.get(0).getOriginalFilename());

        //유저 프로필이미지 경로 업데이트
        User currentUser = user.get();
        currentUser.UpdateProfileImagePath(savedProfileImagePath);
        userRepository.save(currentUser);

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(currentUser).build();
        return userResponseDTO;
    }
    @Override
    public File getProfileImageByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent() == false) {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }
        String imageSavedFolderName = "/users/";
        String imagePath = user.get().getProfileImagePath();
        //imagePath = imagePath.contains(".jpeg") ? imagePath :  imagePath + ".jpeg";
        File file = imageServiceImpl.getFile(imageSavedFolderName, imagePath);
        if(file.exists() == false)
        {
            String errorMessage = "Title image File을 찾을 수 없습니다.";
            throw FileNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
        }
        return file;
    }
    @Override
    public DoListOfUserDTO GetDoListOfUserById(Long id) {
        //내 정보 조회
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent() == false)
        {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //내가 속한 커뮤니티들 조회
        List<DoOfUser> doOfUserList = doOfUserRepository.findByUser(user.get());
        List<Do> doList = new ArrayList<>();
        List<DoResponseDTO> doResponseDTOList = new ArrayList<>();
        for(DoOfUser doOfUser : doOfUserList)
        {
            Do aDo = doOfUser.getMyDo();
            doList.add(aDo);
            DoResponseDTO doResponseDTO = DoResponseDTO.builder().id(aDo.getId()).name(aDo.getName()).description(aDo.getDescription()).place(aDo.getPlace()).bannerImagepath(aDo.getBannerImagepath()).build();
            doResponseDTOList.add(doResponseDTO);
        }

        //반환
        DoListOfUserDTO doListOfUserDTO = DoListOfUserDTO.builder().userId(user.get().getId())
                .doResponseDTOList(doResponseDTOList).build();
        return doListOfUserDTO;
    }

    @Override
    public ScheduleListOfUserDTO GetScheduleListOfUserById(Long id) {
        //내 정보 조회
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent() == false)
        {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //내가 속한 일정들 조회
        List<ScheduleOfUser> scheduleOfUserList = scheduleOfUserRepository.findByUser(user.get());
        List<Schedule> scheduleList = new ArrayList<>();
        for(ScheduleOfUser scheduleOfUser : scheduleOfUserList)
        {
            scheduleList.add(scheduleOfUser.getSchedule());
        }

        //반환
        ScheduleListOfUserDTO scheduleListOfUserDTO = ScheduleListOfUserDTO.builder().user(user.get())
                .scheduleList(scheduleList).build();
        return scheduleListOfUserDTO;
    }


}
