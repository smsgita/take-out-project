package com.dmj.service;

import com.dmj.dto.DishDTO;
import com.dmj.dto.DishPageQueryDTO;
import com.dmj.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dmj.result.PageResult;
import com.dmj.vo.DishVO;

import java.util.List;

/**
* @author dmj
* @description 针对表【dish(菜品)】的数据库操作Service
* @createDate 2024-02-09 16:06:39
*/
public interface DishService{
    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品的批量删除
     * @param ids
     */
    void deleteBatch(Long[] ids);
    /**
     * 根据id查询菜品和对应的口味
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}
