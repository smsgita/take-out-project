package com.dmj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dmj.dto.CategoryPageQueryDTO;
import com.dmj.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dmj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2024-02-09 16:06:39
* @Entity generator.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Page<Category> pageQuery(Page<Object> objectPage, CategoryPageQueryDTO categoryPageQueryDTO);
}




