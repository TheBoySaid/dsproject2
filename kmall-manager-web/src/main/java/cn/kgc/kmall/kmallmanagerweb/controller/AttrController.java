package cn.kgc.kmall.kmallmanagerweb.controller;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.bean.PmsBaseAttrValue;
import cn.kgc.kmall.service.AttrService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AttrController {
    @Reference
    private AttrService attrService;

    @RequestMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(Long catalog3Id) {
        return attrService.attrInfoList(catalog3Id);
    }

    @RequestMapping("/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId) {
        return attrService.getAttrValueList(attrId);
    }

    @RequestMapping("/saveAttrInfo")
    public Integer saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo) {
        return attrService.saveAttrInfo(attrInfo);
    }
}
