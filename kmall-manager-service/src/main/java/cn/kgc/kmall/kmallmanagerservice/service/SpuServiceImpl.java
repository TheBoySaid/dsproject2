package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsBaseSaleAttr;
import cn.kgc.kmall.bean.PmsProductInfo;
import cn.kgc.kmall.bean.PmsProductInfoExample;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsBaseSaleAttrMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsProductInfoMapper;
import cn.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample example = new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        return pmsProductInfoMapper.selectByExample(example);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectByExample(null);
    }

}
