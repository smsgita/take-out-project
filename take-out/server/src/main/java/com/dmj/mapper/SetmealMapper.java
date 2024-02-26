package com.dmj.mapper;

import com.dmj.dto.SetmealPageQueryDTO;
import com.dmj.entity.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.vo.DishItemVO;
import com.dmj.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author dmj
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2024-02-09 16:06:39
* @Entity generator.domain.Setmeal
*/
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Integer contByCategoryId(Long categoryId);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO>  getDishItemBySetmealId(Long setmealId);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}




