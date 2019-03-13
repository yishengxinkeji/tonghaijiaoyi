package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.RotationMapper;
import com.ruoyi.yishengxin.domain.Rotation;
import com.ruoyi.yishengxin.service.IRotationService;
import com.ruoyi.common.support.Convert;

/**
 * 轮播图 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Service
public class RotationServiceImpl implements IRotationService {
    @Autowired
    private RotationMapper rotationMapper;

    /**
     * 查询轮播图信息
     *
     * @param id 轮播图ID
     * @return 轮播图信息
     */
    @Override
    public Rotation selectRotationById(Integer id) {
        return rotationMapper.selectRotationById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param rotation 轮播图信息
     * @return 轮播图集合
     */
    @Override
    public List<Rotation> selectRotationList(Rotation rotation) {
        return rotationMapper.selectRotationList(rotation);
    }

    /**
     * 新增轮播图
     *
     * @param rotation 轮播图信息
     * @return 结果
     */
    @Override
    public int insertRotation(Rotation rotation) {
        return rotationMapper.insertRotation(rotation);
    }

    /**
     * 修改轮播图
     *
     * @param rotation 轮播图信息
     * @return 结果
     */
    @Override
    public int updateRotation(Rotation rotation) {
        return rotationMapper.updateRotation(rotation);
    }

    /**
     * 删除轮播图对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRotationByIds(String ids) {
        return rotationMapper.deleteRotationByIds(Convert.toStrArray(ids));
    }

}
