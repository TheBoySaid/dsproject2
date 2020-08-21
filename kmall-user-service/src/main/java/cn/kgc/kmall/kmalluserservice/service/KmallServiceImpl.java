package cn.kgc.kmall.kmalluserservice.service;

import cn.kgc.kmall.bean.Member;
import cn.kgc.kmall.kmalluserservice.mapper.MemberMapper;
import cn.kgc.kmall.service.KmallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KmallServiceImpl implements KmallService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> selectAllMember() {
        return memberMapper.selectByExample(null);
    }

}
