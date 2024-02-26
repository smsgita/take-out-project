package com.dmj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dmj.annotation.AutoFill;
import com.dmj.dto.DishPageQueryDTO;
import com.dmj.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.enumeration.OperationType;
import com.dmj.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
    @Update("update dish set status = #{status} where id = #{id}")
    void startOrStop(Integer status, Long id);
}




