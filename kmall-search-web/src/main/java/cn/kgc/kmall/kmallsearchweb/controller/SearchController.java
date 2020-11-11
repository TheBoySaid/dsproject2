package cn.kgc.kmall.kmallsearchweb.controller;

import cn.kgc.kmall.bean.*;
import cn.kgc.kmall.service.AttrService;
import cn.kgc.kmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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
        String[] valueId = pmsSearchSkuParam.getValueId();
        if (valueId != null) {
            //封装面包屑数据
            List<PmsSearchCrumb> pmsSearchCrumbList = new ArrayList<>();
            for (String s : valueId) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueId(s);
                pmsSearchCrumb.setUrlParam(getURLParam(pmsSearchSkuParam, s));
                pmsSearchCrumb.setValueName(getValueName(attrList, s));
                pmsSearchCrumbList.add(pmsSearchCrumb);
            }
            model.addAttribute("attrValueSelectedList", pmsSearchCrumbList);
            //利用迭代器排除已选的平台属性，删除集合元素不能使用for循环，因为会出现数组越界
            Iterator<PmsBaseAttrInfo> iterator = attrList.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                for (PmsBaseAttrValue item : pmsBaseAttrInfo.getAttrValueList()) {
                    for (String s : valueId) {
                        if (s.equals(item.getId().toString())) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        model.addAttribute("attrList", attrList);
        //拼接平台属性URL
        String urlParam = getURLParam(pmsSearchSkuParam);
        model.addAttribute("urlParam", urlParam);

        model.addAttribute("keyword", pmsSearchSkuParam.getKeyword());
        return "list";
    }

    //根据valueId查询valueName
    private String getValueName(List<PmsBaseAttrInfo> attrList, String s) {
        String valueName = "";
        for (PmsBaseAttrInfo item : attrList) {
            for (PmsBaseAttrValue item1 : item.getAttrValueList()) {
                if (s.equals(item1.getId().toString())) {
                    valueName = item.getAttrName() + ":" + item1.getValueName();
                }
            }
        }
        return valueName;
    }

    private String getURLParam(PmsSearchSkuParam pmsSearchSkuParam) {
        StringBuffer buffer = new StringBuffer();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueId = pmsSearchSkuParam.getValueId();
        if (StringUtils.isNotBlank(catalog3Id)) {
            buffer.append("&catalog3Id=" + catalog3Id);
        }
        if (StringUtils.isNotBlank(keyword)) {
            buffer.append("&keyword=" + keyword);
        }
        if (valueId != null) {
            for (String item : valueId) {
                buffer.append("&valueId=" + item);
            }
        }
        return buffer.substring(1);
    }

    private String getURLParam(PmsSearchSkuParam pmsSearchSkuParam, String valueId) {
        StringBuffer buffer = new StringBuffer();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueIds = pmsSearchSkuParam.getValueId();
        if (StringUtils.isNotBlank(catalog3Id)) {
            buffer.append("&catalog3Id=" + catalog3Id);
        }
        if (StringUtils.isNotBlank(keyword)) {
            buffer.append("&keyword=" + keyword);
        }
        if (valueId != null) {
            for (String item : valueIds) {
                if (item.equals(valueId) == false)
                    buffer.append("&valueId=" + item);
            }
        }
        return buffer.substring(1);
    }

}
