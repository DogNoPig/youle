package com.youle.item.mapper;

import com.youle.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand(category_id,brand_id) values(#{categoryId},#{brandId})")
    int inserCategoryBrand(@Param("categoryId")Long categoryId,@Param("brandId")Long brandId);

    @Select("SELECT b.id,b.`name`,b.image,b.letter FROM tb_brand b INNER JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    List<Brand> queryByCid(@Param("cid") Long cid);
}
