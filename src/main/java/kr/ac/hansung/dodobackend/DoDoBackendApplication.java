package kr.ac.hansung.dodobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DoDoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoDoBackendApplication.class, args);
    }

}
