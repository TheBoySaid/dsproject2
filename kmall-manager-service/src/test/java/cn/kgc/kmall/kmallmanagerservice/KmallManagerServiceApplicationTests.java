package cn.kgc.kmall.kmallmanagerservice;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.kmallmanagerservice.service.AttrServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KmallManagerServiceApplicationTests {

    @Autowired
    private AttrServiceImpl attrService;


    @Test
    void contextLoads2() {
        List<PmsBaseAttrInfo> list = attrService.attrInfoList(Long.parseLong("61"));
        for (PmsBaseAttrInfo item : list) {
            System.out.println(item);
        }
    }
}
