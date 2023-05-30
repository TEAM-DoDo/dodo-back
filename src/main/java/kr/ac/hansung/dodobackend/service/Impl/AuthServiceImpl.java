package kr.ac.hansung.dodobackend.service.Impl;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import kr.ac.hansung.dodobackend.service.AuthService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;

@Component
public class AuthServiceImpl implements AuthService {
//    private final String authToken;
//    private final String sid;
    private Service service;
    private DefaultMessageService messageService;
    private HashMap<String,String> phoneCertMap = new HashMap<>();

    public AuthServiceImpl(@Value("${twilio.account_sid}") String sid, @Value("${twilio.auth_token}") String authToken, @Value("${twilio.service_id}") String serviceId) {
//        byte[] decodedBytes = Base64.getDecoder().decode(authToken);
//        String decodedString = new String(decodedBytes);
//        this.sid = sid;
//        this.authToken = decodedString;
//        Twilio.init(this.sid, this.authToken);
//        ResourceSet<Service> services = Service.reader().limit(20).read();
//        for(Service record : services) {
//            if (record.getSid().equals(serviceId)){
//                service = record;
//                return;
//            }
//        }
//        //누리고 인증 테스트
        this.messageService = NurigoApp.INSTANCE.initialize("NCS6CFMZBL7DYBS8", "OL23WB98XKU1TDHBUXKGWML1XT8ZTRHP", "https://api.coolsms.co.kr");
    }
    @Override
    public void sendVerification(String phoneNumber)
    {
//        Verification.creator(
//                        service.getSid(),
//                        "+82"+phoneNumber,
//                        "sms")
//                .create();
//        //coolsms를 이용한 코드
        String certNum = createCertNum(6);
        Message message = new Message();
        message.setFrom("01049490367");
        message.setTo(phoneNumber);
        message.setText("인증번호는 " + certNum + "입니다");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
        if (phoneCertMap.containsKey(phoneNumber)){
            phoneCertMap.replace(phoneNumber,certNum);
        }
        else {
            phoneCertMap.put(phoneNumber,certNum);
        }
    }

    private String createCertNum(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++){
            result.append(((int) (Math.random() * 10)));
        }
        return result.toString();
    }

    @Override
    public boolean checkVerification(String phoneNumber,String checkNum)
    {
//        var result = VerificationCheck.creator(service.getSid())
//                .setTo("+82"+phoneNumber)
//                .setCode(checkNum)
//                .create();
//        //coolsms를 이용한 코드
        if (!phoneCertMap.containsKey(phoneNumber)){
            return false;
        }
        String containedCert = phoneCertMap.get(phoneNumber);
        phoneCertMap.remove(phoneNumber);

        return containedCert.equals(checkNum);
    }
}
