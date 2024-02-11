package com.dmj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dmj.constant.MessageConstant;
import com.dmj.context.BaseContext;
import com.dmj.dto.CategoryDTO;
import com.dmj.dto.CategoryPageQueryDTO;
import com.dmj.entity.Category;
import com.dmj.exception.DeletionNotAllowedException;
import com.dmj.mapper.DishMapper;
import com.dmj.mapper.SetmealMapper;
import com.dmj.result.PageResult;
import com.dmj.service.CategoryService;
import com.dmj.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author dmj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2024-02-09 16:06:39
*/
@Service
public class CategoryServiceImpl implements CategoryService{
    @Resource
    CategoryMapper categoryMapper;

    @Resource
    DishMapper dishMapper;

    @Resource
    SetmealMapper setmealMapper;

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.add(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        Page<Category> categoryPage = categoryMapper.pageQuery(new Page<>(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize()),categoryPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(categoryPage.getRecords());
        pageResult.setTotal(categoryPage.getTotal());
        return pageResult;
    }

    @Override
    public void deleteById(Long id) {
        Integer cont = dishMapper.contByCategoryId(id);
        if (cont>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        cont = setmealMapper.contByCategoryId(id);
        if (cont>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = categoryMapper.selectById(id);
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        category.setStatus(status);
        categoryMapper.updateById(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryMapper.selectById(categoryDTO.getId());
        if (category!=null){
            BeanUtils.copyProperties(categoryDTO,category);
//            category.setUpdateUser(BaseContext.getCurrentId());
//            category.setUpdateTime(LocalDateTime.now());
            categoryMapper.update(category);
        }
    }

    @Override
    public List<Category> list(Integer type) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        return categoryMapper.selectList(queryWrapper);
    }
}




