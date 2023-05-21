package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname); //닉네임으로 유저 조회
    User findByPhoneNumber(String phoneNumber); //핸드폰번호로 유저 조회
}
