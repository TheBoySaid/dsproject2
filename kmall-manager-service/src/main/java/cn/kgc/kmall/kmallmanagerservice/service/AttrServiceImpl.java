package cn.kgc.kmall.kmallmanagerservice.service;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.bean.PmsBaseAttrInfoExample;
import cn.kgc.kmall.bean.PmsBaseAttrValue;
import cn.kgc.kmall.bean.PmsBaseAttrValueExample;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsBaseAttrInfoMapper;
import cn.kgc.kmall.kmallmanagerservice.mapper.PmsBaseAttrValueMapper;
import cn.kgc.kmall.service.AttrService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(Long catalog3Id) {
        PmsBaseAttrInfoExample example = new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsBaseAttrInfo> list = pmsBaseAttrInfoMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                PmsBaseAttrValueExample vExample = new PmsBaseAttrValueExample();
                PmsBaseAttrValueExample.Criteria vCriteria = vExample.createCriteria();
                vCriteria.andAttrIdEqualTo(list.get(i).getId());
                list.get(i).setAttrValueList(pmsBaseAttrValueMapper.selectByExample(vExample));
            }
        }
        return list;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId) {
        PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
        criteria.andAttrIdEqualTo(attrId);
        return pmsBaseAttrValueMapper.selectByExample(example);
    }

    @Override
    public Integer saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        int i = 0;
        if (pmsBaseAttrInfo.getId() == null) {
            i = pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);
        } else {
            i = pmsBaseAttrInfoMapper.updateByPrimaryKey(pmsBaseAttrInfo);
            PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
            PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
            criteria.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
            i = pmsBaseAttrValueMapper.deleteByExample(example);
        }
        if (pmsBaseAttrInfo.getAttrValueList().size() > 0) {
            i = pmsBaseAttrValueMapper.insertBatch(pmsBaseAttrInfo.getId(), pmsBaseAttrInfo.getAttrValueList());
        }
        return i;
    }

}
