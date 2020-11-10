package cn.kgc.kmall.service;

import cn.kgc.kmall.bean.PmsBaseAttrInfo;
import cn.kgc.kmall.bean.PmsBaseAttrValue;

import java.util.List;
import java.util.Set;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(Long catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(Long attrId);

    Integer saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> selectAttrInfoValueListByValueId(Set<Long> valueIds);
}
