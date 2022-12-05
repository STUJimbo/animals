package stukk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import stukk.dto.SetmealDto;
import stukk.entity.Setmeal;

import java.util.List;

/**
 * @author wenli
 * @create 2022-09-01 18:09
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     *
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 修改套餐，同时修改套餐和菜品的关联数据
     *
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}
