package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.RollMessageMapper;
import com.ruoyi.yishengxin.domain.RollMessage;
import com.ruoyi.yishengxin.service.IRollMessageService;
import com.ruoyi.common.support.Convert;

/**
 * 滚动消息 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-13
 */
@Service
public class RollMessageServiceImpl implements IRollMessageService {
    @Autowired
    private RollMessageMapper rollMessageMapper;

    /**
     * 查询滚动消息信息
     *
     * @param id 滚动消息ID
     * @return 滚动消息信息
     */
    @Override
    public RollMessage selectRollMessageById(Integer id) {
        return rollMessageMapper.selectRollMessageById(id);
    }

    /**
     * 查询滚动消息列表
     *
     * @param rollMessage 滚动消息信息
     * @return 滚动消息集合
     */
    @Override
    public List<RollMessage> selectRollMessageList(RollMessage rollMessage) {
        return rollMessageMapper.selectRollMessageList(rollMessage);
    }

    /**
     * 新增滚动消息
     *
     * @param rollMessage 滚动消息信息
     * @return 结果
     */
    @Override
    public int insertRollMessage(RollMessage rollMessage) {
        return rollMessageMapper.insertRollMessage(rollMessage);
    }

    /**
     * 修改滚动消息
     *
     * @param rollMessage 滚动消息信息
     * @return 结果
     */
    @Override
    public int updateRollMessage(RollMessage rollMessage) {
        return rollMessageMapper.updateRollMessage(rollMessage);
    }

    /**
     * 删除滚动消息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRollMessageByIds(String ids) {
        return rollMessageMapper.deleteRollMessageByIds(Convert.toStrArray(ids));
    }

}
