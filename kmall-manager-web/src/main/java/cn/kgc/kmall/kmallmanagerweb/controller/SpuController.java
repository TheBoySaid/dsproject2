package cn.kgc.kmall.kmallmanagerweb.controller;

import cn.kgc.kmall.bean.PmsBaseSaleAttr;
import cn.kgc.kmall.bean.PmsProductInfo;
import cn.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;


import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class SpuController {
    @Reference
    private SpuService spuService;

    @Value("${fileServer.url}")
    String fileUrl;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        return spuService.spuList(catalog3Id);
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        String imgUrl = fileUrl;
        if (file != null) {
            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            String filename = file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(filename);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            imgUrl = fileUrl;
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl += "/" + path;
            }
        }
        System.out.println(imgUrl);
        return imgUrl;
    }

    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return spuService.baseSaleAttrList();
    }

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }
}
