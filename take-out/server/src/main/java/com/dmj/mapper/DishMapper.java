package com.dmj.mapper;

import com.dmj.annotation.AutoFill;
import com.dmj.dto.DishPageQueryDTO;
import com.dmj.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.enumeration.OperationType;
import com.dmj.vo.DishVO;
import com.github.pagehelper.Page;
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

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    int contByCategoryId(Long categoryId);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @AutoFill(OperationType.UPDATE)
   default int IUpdateById(Dish dish){
        return this.updateById(dish);
    };
}




