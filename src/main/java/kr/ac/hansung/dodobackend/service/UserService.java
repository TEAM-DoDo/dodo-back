package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dao.UserDao;
import kr.ac.hansung.dodobackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User GetUserById(int id) {
        User user = userDao.findById(id).orElse(null);
        return user;
    }
    //테스트를 위해 작성한 함수
    public User GetUserByNameAndPhone(String nickName,String phoneNumber){
        return userDao.findUserByNicknameAndPhoneNumber(nickName,phoneNumber);
    }
    public User GetUserByPhone(String phoneNumber){
        return userDao.findUserByPhoneNumber(phoneNumber);
    }
    public void AddUser(User user)
    {
        userDao.save(user);
    }

}
