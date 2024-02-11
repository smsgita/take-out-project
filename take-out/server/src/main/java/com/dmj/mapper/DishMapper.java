package com.dmj.mapper;

import com.dmj.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author dmj
* @description 针对表【dish(菜品)】的数据库操作Mapper
* @createDate 2024-02-09 16:06:39
* @Entity generator.domain.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    int contByCategoryId(Long categoryId);

}




