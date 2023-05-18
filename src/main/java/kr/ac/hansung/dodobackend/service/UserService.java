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

    public User GetUserById(String id) {
        User user = userDao.findById(id).orElse(null);
        return user;
    }

    public void AddUser(User user)
    {
        userDao.save(user);
    }

}
