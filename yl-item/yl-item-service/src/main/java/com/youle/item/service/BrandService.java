package com.youle.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youle.common.enums.ExceptionEnums;
import com.youle.common.exception.YlException;
import com.youle.common.vo.PageResult;
import com.youle.item.mapper.BrandMapper;
import com.youle.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 品牌服务层
 * @author xw
 * @date 2019/5/30 16:29
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页

        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)){
            // 过滤条件
            example.createCriteria().orLike("name","%"+key+"%")
                    .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClaus = sortBy +" "+ (!"".equals(desc) ? "desc":"ASC");

            example.setOrderByClause(orderByClaus);
        }
        //查询
        PageHelper.startPage(page,rows);
        List<Brand> list = brandMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)){
            throw new YlException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        brand.setId(null);
        int count = brandMapper.insertSelective(brand);
        if (count != 1){
            throw new YlException(ExceptionEnums.BRAND_SAVE_ERROR);
        }
        //关联商品分类和品牌
        for (Long cid:cids){
            count = brandMapper.inserCategoryBrand(cid, brand.getId());
            if (count != 1){
                throw new YlException(ExceptionEnums.BRAND_SAVE_ERROR);
            }
        }
    }

    public Brand queryById(Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null){
            throw new YlException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return brand;
    }

    public List<Brand> queryByCid(Long cid) {
        List<Brand> brands = brandMapper.queryByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            throw new YlException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return brands;
    }
}
