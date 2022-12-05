package stukk.entity;

import com.alibaba.druid.filter.AutoLoad;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_cat")
public class Cat {

    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String url;
    private String weight;
    private String allName;
    private String ability;
    private String position;
    private String life;
    private String wool;
    private String type;
    private String englishName;
    private String height;
    private String abs;
    private String image;

}
