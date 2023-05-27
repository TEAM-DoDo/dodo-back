package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.DoOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.DoOfUserRepository;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.DoOfUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoOfUserServiceImpl implements DoOfUserService {
    private final UserRepository userRepository;
    private final DoRepository doRepository;
    private final DoOfUserRepository doOfUserRepository;

    @Override
    public void CreateDoOfUser(DoEnterDTO doEnterDTO)
    {
        Long userId = doEnterDTO.getUserId();
        Long doId = doEnterDTO.getDoId();

        //조회
        Optional<User> user = userRepository.findById(userId);
        Optional<Do> Do = doRepository.findById(doId);

        //저장
        DoOfUser newDoOfUser = DoOfUser.builder().user(user.get()).myDo(Do.get())
                .isHostTrue(false).isLikeTrue(false).build();
        doOfUserRepository.save(newDoOfUser);
    }

}
