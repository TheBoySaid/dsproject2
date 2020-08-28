package cn.kgc.kmall.service;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.bean.PmsBaseCatalog1;
import cn.kgc.kmall.bean.PmsBaseCatalog2;
import cn.kgc.kmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(Long catalog2Id);

}
