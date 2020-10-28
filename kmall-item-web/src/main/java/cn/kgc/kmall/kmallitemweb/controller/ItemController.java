package cn.kgc.kmall.kmallitemweb.controller;

import cn.kgc.kmall.bean.PmsSkuInfo;
import cn.kgc.kmall.bean.PmsSkuSaleAttrValue;
import cn.kgc.kmall.service.SkuService;
import cn.kgc.kmall.service.SpuService;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    private SkuService skuService;

    @Reference
    private SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable Long skuId, Model model) {
        PmsSkuInfo pmsSkuInfo = skuService.selectBySkuId(skuId);
        model.addAttribute("skuInfo", pmsSkuInfo);
        model.addAttribute("spuSaleAttrListCheckBySku", spuService.spuSaleAttrListIsCheck(pmsSkuInfo.getSpuId(), skuId));
        List<PmsSkuInfo> pmsSkuInfoList = skuService.selectSkuBySpuId(pmsSkuInfo.getSpuId());
        Map<String, Object> map = new HashMap<>();
        for (PmsSkuInfo skuItem : pmsSkuInfoList) {
            String k = "";
            String v = skuItem.getId() + "";
            for (PmsSkuSaleAttrValue saleItem : skuItem.getSkuSaleAttrValueList()) {
                k += saleItem.getSaleAttrValueId() + "|";
            }
            map.put(k, v);
        }
        model.addAttribute("skuSaleAttrHashJsonStr", JSON.toJSONString(map));
        return "item";
    }
}
