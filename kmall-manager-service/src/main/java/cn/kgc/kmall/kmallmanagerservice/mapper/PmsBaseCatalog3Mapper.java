package cn.kgc.kmall.kmallmanagerservice.mapper;

import cn.kgc.kmall.bean.PmsBaseCatalog3;
import cn.kgc.kmall.bean.PmsBaseCatalog3Example;
import org.apache.ibatis.annotations.Param;

public interface PmsBaseCatalog3Mapper {
    int countByExample(PmsBaseCatalog3Example example);

    int deleteByExample(PmsBaseCatalog3Example example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsBaseCatalog3 record);

    int insertSelective(PmsBaseCatalog3 record);

    java.util.List<PmsBaseCatalog3> selectByExample(PmsBaseCatalog3Example example);

    PmsBaseCatalog3 selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsBaseCatalog3 record, @Param("example") PmsBaseCatalog3Example example);

    int updateByExample(@Param("record") PmsBaseCatalog3 record, @Param("example") PmsBaseCatalog3Example example);

    int updateByPrimaryKeySelective(PmsBaseCatalog3 record);

    int updateByPrimaryKey(PmsBaseCatalog3 record);
}