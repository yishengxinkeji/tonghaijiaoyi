package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.vipUser.VipAddressMapper;
import com.ruoyi.yishengxin.domain.vipUser.VipAddress;
import com.ruoyi.yishengxin.service.IVipAddressService;
import com.ruoyi.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员地址 服务层实现
 *
 * @author ruoyi
 * @date 2019-02-27
 */
@Service
public class VipAddressServiceImpl implements IVipAddressService {
    @Autowired
    private VipAddressMapper vipAddressMapper;

    /**
     * 查询会员地址信息
     *
     * @param id 会员地址ID
     * @return 会员地址信息
     */
    @Override
    public VipAddress selectVipAddressById(Integer id) {
        return vipAddressMapper.selectVipAddressById(id);
    }

    /**
     * 查询会员地址列表
     *
     * @param vipAddress 会员地址信息
     * @return 会员地址集合
     */
    @Override
    public List<VipAddress> selectVipAddressList(VipAddress vipAddress) {
        return vipAddressMapper.selectVipAddressList(vipAddress);
    }

    /**
     * 新增会员地址
     *
     * @param vipAddress 会员地址信息
     * @return 结果
     */
    @Override
    public int insertVipAddress(VipAddress vipAddress) {
        return vipAddressMapper.insertVipAddress(vipAddress);
    }

    /**
     * 修改会员地址
     *
     * @param vipAddress 会员地址信息
     * @return 结果
     */
    @Override
    public int updateVipAddress(VipAddress vipAddress) {
        return vipAddressMapper.updateVipAddress(vipAddress);
    }

    /**
     * 删除会员地址对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVipAddressByIds(String ids) {
        return vipAddressMapper.deleteVipAddressByIds(Convert.toStrArray(ids));
    }

    /**
     * 将该用户的地址改为非默认
     * @param vipId
     */
    @Override
    public int updateDefaultAddress(Integer vipId) {
        return vipAddressMapper.updateDefaultAddress(vipId);
    }

}
