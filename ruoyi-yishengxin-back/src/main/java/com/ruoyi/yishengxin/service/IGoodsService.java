package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Goods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IGoodsService {

    /**
     *
     * @return 分页查询商品 根据效果暂时显示第一页第一条已上架的商品
     */

    public List<Goods> selectGoods();

}
