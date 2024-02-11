package com.dmj.mapper;

import com.dmj.entity.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dmj
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2024-02-09 16:06:39
* @Entity generator.domain.Setmeal
*/
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Integer contByCategoryId(Long categoryId);
}




