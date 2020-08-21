package cn.kgc.kmall.kmalluserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.kgc.kmall.kmalluserservice.mapper")
@SpringBootApplication
public class KmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmallUserServiceApplication.class, args);
    }

}
