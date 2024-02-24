package com.dmj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dmj.constant.MessageConstant;
import com.dmj.constant.StatusConstant;
import com.dmj.dto.DishDTO;
import com.dmj.dto.DishPageQueryDTO;
import com.dmj.entity.Dish;
import com.dmj.entity.DishFlavor;
import com.dmj.entity.SetmealDish;
import com.dmj.exception.DeletionNotAllowedException;
import com.dmj.mapper.DishFlavorMapper;
import com.dmj.mapper.SetmealDishMapper;
import com.dmj.result.PageResult;
import com.dmj.service.DishService;
import com.dmj.mapper.DishMapper;
import com.dmj.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author dmj
* @description 针对表【dish(菜品)】的数据库操作Service实现
* @createDate 2024-02-09 16:06:39
*/
@Service
public class DishServiceImpl implements DishService{
    @Resource
    DishMapper dishMapper;

    @Resource
    DishFlavorMapper dishFlavorMapper;

    @Resource
    SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        // 向菜品表插入数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long id = dish.getId();

        // 向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     *菜品的批量删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        // 判断当前菜品能否删除 --是否存在在起售中的菜品
        for (Long id : ids) {
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus() == StatusConstant.ENABLE){
                // 当前菜品在起售中不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 判断当前菜品能否删除 --是否被套餐关联
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getDishId,ids);
        Long count = setmealDishMapper.selectCount(queryWrapper);
        if (count != null && count > 0){
            // 当前菜品被套餐关联了，无法删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 删除菜品表中的菜品数据
        LambdaQueryWrapper<Dish> deleteDish = new LambdaQueryWrapper<>();
        deleteDish.in(Dish::getId,ids);
        dishMapper.delete(deleteDish);
        // 删除菜品关联的口味数据
        LambdaQueryWrapper<DishFlavor> deleteDishFlavor = new LambdaQueryWrapper<>();
        deleteDishFlavor.in(DishFlavor::getDishId,ids);
        dishFlavorMapper.delete(deleteDishFlavor);
    }

    /**
     * 根据id查询菜品和对应的口味
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 根据id查询菜品数据
        Dish dish = dishMapper.selectById(id);

        // 根据菜品id查询口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(queryWrapper);

        // 将查询到的数据进行封装
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        // 转为菜品实体对象，补全修改数据
        BeanUtils.copyProperties(dishDTO, dish);

        // 修改菜品
        int i = dishMapper.IUpdateById(dish);

        // 删除原有的口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
        dishFlavorMapper.delete(queryWrapper);

        // 重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);

        }
    }
}




