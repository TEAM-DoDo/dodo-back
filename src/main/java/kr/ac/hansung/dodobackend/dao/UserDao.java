package kr.ac.hansung.dodobackend.dao;

import kr.ac.hansung.dodobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByNicknameAndPhoneNumber(String nickName, String phoneNumber);
    User findUserByPhoneNumber(String phoneNumber);
}
