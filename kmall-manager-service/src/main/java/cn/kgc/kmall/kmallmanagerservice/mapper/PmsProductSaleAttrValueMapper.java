package cn.kgc.kmall.kmallmanagerservice.mapper;

import cn.kgc.kmall.bean.PmsProductSaleAttrValue;
import cn.kgc.kmall.bean.PmsProductSaleAttrValueExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductSaleAttrValueMapper {
    int countByExample(PmsProductSaleAttrValueExample example);

    int deleteByExample(PmsProductSaleAttrValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductSaleAttrValue record);

    int insertSelective(PmsProductSaleAttrValue record);

    List<PmsProductSaleAttrValue> selectByExample(PmsProductSaleAttrValueExample example);

    PmsProductSaleAttrValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductSaleAttrValue record, @Param("example") PmsProductSaleAttrValueExample example);

    int updateByExample(@Param("record") PmsProductSaleAttrValue record, @Param("example") PmsProductSaleAttrValueExample example);

    int updateByPrimaryKeySelective(PmsProductSaleAttrValue record);

    int updateByPrimaryKey(PmsProductSaleAttrValue record);

    int insertBatch(@Param("spuId") Long spuId, @Param("spuSaleAttrValueList") List<PmsProductSaleAttrValue> spuSaleAttrValueList);
}