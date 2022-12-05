package stukk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stukk.entity.DishFlavor;
import stukk.mapper.DishFlavorMapper;
import stukk.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author wenli
 * @create 2022-09-02 0:32
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
