package com.ruoyi.yishengxin.service.impl;

import com.ruoyi.yishengxin.domain.Goods;
import com.ruoyi.yishengxin.mapper.GoodsMapper;
import com.ruoyi.yishengxin.service.IGoodsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class IGoodsServiceImpl implements IGoodsService {

    @Resource
    GoodsMapper GoodsMapper;


    @Override
    public List<Goods> selectGoods() {
        return GoodsMapper.selectGoods();
    }
}
