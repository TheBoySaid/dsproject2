package cn.kgc.kmall.service;

import cn.kgc.kmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo selectBySkuId(Long id);

    List<PmsSkuInfo> selectSkuBySpuId(Long spuId);
}
