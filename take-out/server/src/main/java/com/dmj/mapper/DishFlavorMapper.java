package com.dmj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author dmj
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2024-02-14 12:38:14
* @Entity generator.domain.DishFlavor
*/
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    void insertBatch(List<DishFlavor> flavors);
}




