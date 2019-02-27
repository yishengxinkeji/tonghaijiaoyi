package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.VipUserMapper;
import com.ruoyi.yishengxin.domain.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import com.ruoyi.common.support.Convert;

/**
 * 会员基本 服务层实现
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
public class VipUserServiceImpl implements IVipUserService {
    @Autowired
    private VipUserMapper vipUserMapper;

    /**
     * 查询会员基本信息
     *
     * @param id 会员基本ID
     * @return 会员基本信息
     */
    @Override
    public VipUser selectVipUserById(Integer id) {
        return vipUserMapper.selectVipUserById(id);
    }

    /**
     * 查询会员基本列表
     *
     * @param vipUser 会员基本信息
     * @return 会员基本集合
     */
    @Override
    public List<VipUser> selectVipUserList(VipUser vipUser) {
        return vipUserMapper.selectVipUserList(vipUser);
    }

    /**
     * 新增会员基本
     *
     * @param vipUser 会员基本信息
     * @return 结果
     */
    @Override
    public int insertVipUser(VipUser vipUser) {
        return vipUserMapper.insertVipUser(vipUser);
    }

    /**
     * 修改会员基本
     *
     * @param vipUser 会员基本信息
     * @return 结果
     */
    @Override
    public int updateVipUser(VipUser vipUser) {
        return vipUserMapper.updateVipUser(vipUser);
    }

    /**
     * 删除会员基本对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipUserByIds(String ids) {
        return vipUserMapper.deleteVipUserByIds(Convert.toStrArray(ids));
    }

}
