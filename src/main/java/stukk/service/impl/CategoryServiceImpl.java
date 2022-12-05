package stukk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stukk.common.CustomException;
import stukk.entity.Category;
import stukk.entity.Dish;
import stukk.entity.Setmeal;
import stukk.mapper.CategoryMapper;
import stukk.service.CategoryService;
import stukk.service.DishService;
import stukk.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wenli
 * @create 2022-09-01 16:14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id来查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除该分类！");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id来查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除该分类！");
        }

        // 正常删除分类
        super.removeById(id);
    }
}
