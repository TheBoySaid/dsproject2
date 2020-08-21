package cn.kgc.kmall.kmalluserservice;

import cn.kgc.kmall.bean.Member;
import cn.kgc.kmall.service.KmallService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KmallUserServiceApplicationTests {

    @Autowired
    KmallService kmallService;

    @Test
    void contextLoads() {
        List<Member> list = kmallService.selectAllMember();
        for (Member item:
             list) {
            System.out.println(item);
        }
    }

}
