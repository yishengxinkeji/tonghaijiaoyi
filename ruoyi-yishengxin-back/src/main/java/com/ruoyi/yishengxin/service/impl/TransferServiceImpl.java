package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.TransferMapper;
import com.ruoyi.yishengxin.domain.Transfer;
import com.ruoyi.yishengxin.service.ITransferService;
import com.ruoyi.common.support.Convert;

/**
 * 转入转出说明 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-28
 */
@Service
public class TransferServiceImpl implements ITransferService {
    @Autowired
    private TransferMapper transferMapper;

    /**
     * 查询转入转出说明信息
     *
     * @param id 转入转出说明ID
     * @return 转入转出说明信息
     */
    @Override
    public Transfer selectTransferById(Integer id) {
        return transferMapper.selectTransferById(id);
    }

    /**
     * 查询转入转出说明列表
     *
     * @param transfer 转入转出说明信息
     * @return 转入转出说明集合
     */
    @Override
    public List<Transfer> selectTransferList(Transfer transfer) {
        return transferMapper.selectTransferList(transfer);
    }

    /**
     * 新增转入转出说明
     *
     * @param transfer 转入转出说明信息
     * @return 结果
     */
    @Override
    public int insertTransfer(Transfer transfer) {
        return transferMapper.insertTransfer(transfer);
    }

    /**
     * 修改转入转出说明
     *
     * @param transfer 转入转出说明信息
     * @return 结果
     */
    @Override
    public int updateTransfer(Transfer transfer) {
        return transferMapper.updateTransfer(transfer);
    }

    /**
     * 删除转入转出说明对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTransferByIds(String ids) {
        return transferMapper.deleteTransferByIds(Convert.toStrArray(ids));
    }

}
