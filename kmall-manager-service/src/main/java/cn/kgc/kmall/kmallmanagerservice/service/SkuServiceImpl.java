package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsSkuAttrValue;
import cn.kgc.kmall.bean.PmsSkuImage;
import cn.kgc.kmall.bean.PmsSkuInfo;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuAttrValueMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuImageMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuInfoMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsSkuSaleAttrValueMapper;
import cn.kgc.kmall.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveSkuInfo(PmsSkuInfo skuInfo) {
        int i = 0 ;
        i = pmsSkuInfoMapper.insert(skuInfo);
        if (i > 0) {
            i = pmsSkuImageMapper.insertBatch(skuInfo.getSkuImageList(), skuInfo.getId());
            i = pmsSkuAttrValueMapper.insertBatch(skuInfo.getSkuAttrValueList(), skuInfo.getId());
            i = pmsSkuSaleAttrValueMapper.insertBatch(skuInfo.getSkuSaleAttrValueList(), skuInfo.getId());
        }
    }
}
