package cn.kgc.kmall.kmallsearchweb.controller;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.bean.PmsSearchSkuInfo;
import cn.kgc.kmall.bean.PmsSearchSkuParam;
import cn.kgc.kmall.bean.PmsSkuAttrValue;
import cn.kgc.kmall.service.AttrService;
import cn.kgc.kmall.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("/list.html")
    public String List(PmsSearchSkuParam pmsSearchSkuParam, Model model) {
        List<PmsSearchSkuInfo> skuLsInfoList = searchService.list(pmsSearchSkuParam);
        model.addAttribute("skuLsInfoList", skuLsInfoList);
        Set<Long> set = new HashSet<>();
        for (PmsSearchSkuInfo item : skuLsInfoList) {
            for (PmsSkuAttrValue item2 : item.getSkuAttrValueList()) {
                set.add(item2.getValueId());
            }
        }
        List<PmsBaseAttrInfo> attrList = attrService.selectAttrInfoValueListByValueId(set);
        model.addAttribute("attrList", attrList);
        return "list";
    }
}
