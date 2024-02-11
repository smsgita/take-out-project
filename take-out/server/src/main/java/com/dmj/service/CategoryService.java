package com.dmj.service;

import com.dmj.dto.CategoryDTO;
import com.dmj.dto.CategoryPageQueryDTO;
import com.dmj.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dmj.result.PageResult;

import java.util.List;

/**
* @author dmj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2024-02-09 16:06:39
*/
public interface CategoryService{

    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void startOrStop(Integer status, Long id);

    void update(CategoryDTO categoryDTO);

    List<Category> list(Integer type);
}
