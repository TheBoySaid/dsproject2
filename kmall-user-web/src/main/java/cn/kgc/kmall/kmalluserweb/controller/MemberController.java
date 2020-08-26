package cn.kgc.kmall.kmalluserweb.controller;

import cn.kgc.kmall.bean.Member;
import cn.kgc.kmall.service.KmallService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemberController {
    @Reference
    KmallService kmallService;

    @RequestMapping("/test")
    @ResponseBody
    public List<Member> test(){
        List<Member> members = kmallService.selectAllMember();
        return members;
    }

}
