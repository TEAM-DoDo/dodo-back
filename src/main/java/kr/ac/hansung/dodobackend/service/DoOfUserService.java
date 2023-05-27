package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.entity.User;

import java.util.List;

public interface DoOfUserService {
    void CreateDoOfUser(DoEnterDTO doEnterDTO);
    List<User> getUserListOfDo(Long doId);
}
