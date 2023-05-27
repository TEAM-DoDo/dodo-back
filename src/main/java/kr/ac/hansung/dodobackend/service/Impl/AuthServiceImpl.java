package kr.ac.hansung.dodobackend.service.Impl;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import kr.ac.hansung.dodobackend.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {
    private final String authToken;
    private final String sid;
    private Service service;

    public AuthServiceImpl(@Value("${twilio.account_sid}") String sid, @Value("${twilio.auth_token}") String authToken, @Value("${twilio.service_id}") String serviceId) {
        this.sid = sid;
        this.authToken = authToken;
        Twilio.init(sid, authToken);
        ResourceSet<Service> services = Service.reader().limit(20).read();
        for(Service record : services) {
            if (record.getSid().equals(serviceId)){
                service = record;
                return;
            }
        }
    }

    @Override
    public void sendVerification(String phoneNumber)
    {
        Verification.creator(
                        service.getSid(),
                        "+82"+phoneNumber,
                        "sms")
                .create();
    }

    @Override
    public boolean checkVerification(String phoneNumber,String checkNum)
    {
        var result = VerificationCheck.creator(service.getSid())
                .setTo("+82"+phoneNumber)
                .setCode(checkNum)
                .create();
        return result.getStatus().equals("approved");
    }
}