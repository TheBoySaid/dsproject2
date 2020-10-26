package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsBaseSaleAttr;
import cn.kgc.kmall.bean.PmsProductInfo;
import cn.kgc.kmall.bean.PmsProductInfoExample;
import cn.kgc.kmall.kmallmanagerservice.mapper.*;
import cn.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;

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

    @Override
    @Transactional
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfoMapper.insert(pmsProductInfo);
        Long spuId = pmsProductInfo.getId();
        pmsProductImageMapper.insertBatch(spuId, pmsProductInfo.getSpuImageList());
        pmsProductSaleAttrMapper.insertBath(spuId, pmsProductInfo.getSpuSaleAttrList());
        for (int i = 0; i < pmsProductInfo.getSpuSaleAttrList().size(); i++) {
            pmsProductSaleAttrValueMapper.insertBatch(spuId, pmsProductInfo.getSpuSaleAttrList().get(i).getSpuSaleAttrValueList());
        }
    }

}
