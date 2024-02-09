package com.dmj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dmj.entity.Category;
import com.dmj.service.CategoryService;
import com.dmj.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author dmj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2024-02-09 16:06:39
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




