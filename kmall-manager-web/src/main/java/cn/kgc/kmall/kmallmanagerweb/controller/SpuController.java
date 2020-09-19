package cn.kgc.kmall.kmallmanagerweb.controller;

import cn.kgc.kmall.bean.PmsBaseSaleAttr;
import cn.kgc.kmall.bean.PmsProductInfo;
import cn.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class SpuController {
    @Reference
    private SpuService spuService;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        return spuService.spuList(catalog3Id);
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        return "https://m.360buyimg.com/babel/jfs/t5137/20/1794970752/352145/d56e4e94/591417dcN4fe5ef33.jpg";
    }

    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return spuService.baseSaleAttrList();
    }

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        return "success";
    }
}
