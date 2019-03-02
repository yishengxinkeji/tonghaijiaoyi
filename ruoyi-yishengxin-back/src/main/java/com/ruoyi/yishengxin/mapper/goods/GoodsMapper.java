package com.ruoyi.yishengxin.mapper.goods;

import com.ruoyi.yishengxin.domain.goods.Goods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsMapper {


    /**
     *
     * @return 分页查询商品 根据效果暂时显示第一页第一条已上架的商品
     */
    @Select("SELECT * FROM ysx_goods where standUpAndDown = 1 LIMIT 0, 1;")
    public List<Goods>  selectGoods();

}
