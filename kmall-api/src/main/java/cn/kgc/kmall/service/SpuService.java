package cn.kgc.kmall.service;

import cn.kgc.kmall.bean.PmsBaseSaleAttr;
import cn.kgc.kmall.bean.PmsProductInfo;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(Long catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
