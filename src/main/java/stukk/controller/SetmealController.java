package stukk.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import stukk.common.R;
import stukk.dto.SetmealDto;
import stukk.entity.Category;
import stukk.entity.Dish;
import stukk.entity.Setmeal;
import stukk.entity.SetmealDish;
import stukk.service.CategoryService;
import stukk.service.DishService;
import stukk.service.SetmealDishService;
import stukk.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenli
 * @create 2022-09-02 20:07
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    // 清除所有套餐缓存
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功！");
    }

    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper1.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        // 添加排序条件
        queryWrapper1.orderByDesc(Setmeal::getUpdateTime);
        // 执行查询
        setmealService.page(pageInfo, queryWrapper1);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> setmealDtos = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            // 对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            // 获取分类id
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                // 设置分类名称
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(setmealDtos);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    // 清除所有套餐缓存
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("成功删除套餐！");
    }

    /**
     * 根据id修改套餐状态
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status, @RequestParam List<Long> ids) {
        List<Setmeal> list = new ArrayList<>();
        for (Long id : ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            list.add(setmeal);
        }
        setmealService.updateBatchById(list);
        return R.success("修改套餐状态成功！");
    }

    /**
     * 根据id获取套餐信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        // 对象拷贝
        BeanUtils.copyProperties(setmeal, setmealDto);
        // 获取分类名称
        Category category = categoryService.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        // 获取套餐对应的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }

    /**
     * 更新套餐
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    // 清除所有套餐缓存
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        // 删除套餐原本关联的菜品信息
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);

        // 保存更新后套餐的信息
        setmealService.updateWithDish(setmealDto);
        return R.success("修改套餐成功！");
    }

    /**
     * 根据分类id查询套餐信息
     *
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<SetmealDto>> list(Setmeal setmeal) {
        // 根据分类id获取套餐信息
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Setmeal::getCategoryId, setmeal.getCategoryId());
        lambdaQueryWrapper.eq(Setmeal::getStatus, setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);
        List<SetmealDto> dtoList = new ArrayList<>();
        for (Setmeal setmeal1 : list) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal1, setmealDto);
            dtoList.add(setmealDto);
        }

        // 根据套餐id获取套餐菜品信息
        for (SetmealDto setmealDto : dtoList) {
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
            List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
            setmealDto.setSetmealDishes(setmealDishes);
        }

        return R.success(dtoList);
    }

    /**
     * 根据套餐id获取套餐菜品信息
     *
     * @param setmealId
     * @return
     */
    @GetMapping("/dish/{setmealId}")
    public R<List<SetmealDish>> getDish(@PathVariable Long setmealId) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        List<SetmealDish> dishes = setmealDishService.list(queryWrapper);
        dishes.stream().map((item) -> {
            Dish dish = dishService.getById(item.getDishId());
            item.setImage(dish.getImage());
            return item;
        }).collect(Collectors.toList());
        return R.success(dishes);
    }
}
