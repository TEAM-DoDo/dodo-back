package kr.ac.hansung.dodobackend.service;

public interface AuthService {
    void sendVerification(String phoneNumber);
    boolean checkVerification(String phoneNumber,String checkNum);
}
