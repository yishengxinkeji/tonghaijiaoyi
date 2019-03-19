package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipAboutusMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipAboutus;
import com.ruoyi.yishengxin.service.IVipAboutusService;
import com.ruoyi.common.support.Convert;

/**
 * 关于我们 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-02
 */
@Service
public class VipAboutusServiceImpl implements IVipAboutusService {
    @Autowired
    private VipAboutusMapper vipAboutusMapper;

    /**
     * 查询关于我们信息
     *
     * @param id 关于我们ID
     * @return 关于我们信息
     */
    @Override
    public VipAboutus selectVipAboutusById(Integer id) {
        return vipAboutusMapper.selectVipAboutusById(id);
    }

    /**
     * 查询关于我们列表
     *
     * @param vipAboutus 关于我们信息
     * @return 关于我们集合
     */
    @Override
    public List<VipAboutus> selectVipAboutusList(VipAboutus vipAboutus) {
        return vipAboutusMapper.selectVipAboutusList(vipAboutus);
    }

    /**
     * 新增关于我们
     *
     * @param vipAboutus 关于我们信息
     * @return 结果
     */
    @Override
    public int insertVipAboutus(VipAboutus vipAboutus) {
        return vipAboutusMapper.insertVipAboutus(vipAboutus);
    }

    /**
     * 修改关于我们
     *
     * @param vipAboutus 关于我们信息
     * @return 结果
     */
    @Override
    public int updateVipAboutus(VipAboutus vipAboutus) {
        return vipAboutusMapper.updateVipAboutus(vipAboutus);
    }

    /**
     * 删除关于我们对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipAboutusByIds(String ids) {
        return vipAboutusMapper.deleteVipAboutusByIds(Convert.toStrArray(ids));
    }

}
