package cn.kgc.kmall.service;

import cn.kgc.kmall.bean.PmsSearchSkuInfo;
import cn.kgc.kmall.bean.PmsSearchSkuParam;
import cn.kgc.kmall.bean.PmsSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchSkuParam pmsSearchSkuParam);
}
