package com.dmj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dmj.context.BaseContext;
import com.dmj.dto.ShoppingCartDTO;
import com.dmj.entity.Dish;
import com.dmj.entity.Setmeal;
import com.dmj.entity.ShoppingCart;
import com.dmj.mapper.DishMapper;
import com.dmj.mapper.SetmealMapper;
import com.dmj.mapper.ShoppingCartMapper;
import com.dmj.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Resource
    DishMapper dishMapper;

    @Resource
    SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前加入到购物车中的商品是否已经存在
        BeanUtils.copyProperties(shoppingCartDTO,new ShoppingCart());
        List<ShoppingCart> list = listByShoppingCartDTO(shoppingCartDTO);
        // 如果已经存在将对应的商品数加一即可
        if (list != null && list.size() > 0){
            ShoppingCart shoppingCart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        } else {
            // 如果不存再添加一条将商品加入
            Long dishId = shoppingCartDTO.getDishId();

            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            if (dishId != null){
                // dishId不为空表示本次添加的是菜品
                Dish dish = dishMapper.selectById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else {
                // 本次添加到购物车的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectList(queryWrapper);
        return shoppingCartList;
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartMapper.delete(queryWrapper);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前加入到购物车中的商品是否已经存在
        BeanUtils.copyProperties(shoppingCartDTO,new ShoppingCart());
        List<ShoppingCart> list = listByShoppingCartDTO(shoppingCartDTO);
        // 如果已经存在将对应的商品数加一即可
        if (list != null && list.size() > 0){
            ShoppingCart shoppingCart = list.get(0);
            Integer number = shoppingCart.getNumber();
            shoppingCart.setNumber(number - 1);
            if (number > 1){
                // 数量大于一数量减一
                shoppingCartMapper.updateNumberById(shoppingCart);
            }else {
                // 不大于一清除数据
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }
        }
    }

    public List<ShoppingCart> listByShoppingCartDTO(ShoppingCartDTO shoppingCartDTO){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        queryWrapper.eq(shoppingCartDTO.getSetmealId() != null,ShoppingCart::getSetmealId,shoppingCartDTO.getSetmealId());
        queryWrapper.eq(shoppingCartDTO.getDishId() != null,ShoppingCart::getDishId,shoppingCartDTO.getDishId());
        queryWrapper.eq(shoppingCartDTO.getDishFlavor() != null,ShoppingCart::getDishFlavor,shoppingCartDTO.getDishFlavor());

        return shoppingCartMapper.selectList(queryWrapper);
    }
}
