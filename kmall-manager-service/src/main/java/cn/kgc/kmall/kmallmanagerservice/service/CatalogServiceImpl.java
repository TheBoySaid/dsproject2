package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsBaseCatalog1;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsBaseCatalog1Mapper;
import cn.kgc.kmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1s = pmsBaseCatalog1Mapper.selectByExample(null);
        return pmsBaseCatalog1s;

    }
}
