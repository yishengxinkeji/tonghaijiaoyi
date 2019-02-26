package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.PullMessageMapper;
import com.ruoyi.yishengxin.domain.PullMessage;
import com.ruoyi.yishengxin.service.IPullMessageService;
import com.ruoyi.common.support.Convert;

/**
 * 推送消息 服务层实现
 *
 * @author ruoyi
 * @date 2019-02-26
 */
@Service
public class PullMessageServiceImpl implements IPullMessageService {
    @Autowired
    private PullMessageMapper pullMessageMapper;

    /**
     * 查询推送消息信息
     *
     * @param id 推送消息ID
     * @return 推送消息信息
     */
    @Override
    public PullMessage selectPullMessageById(Integer id) {
        return pullMessageMapper.selectPullMessageById(id);
    }

    /**
     * 查询推送消息列表
     *
     * @param pullMessage 推送消息信息
     * @return 推送消息集合
     */
    @Override
    public List<PullMessage> selectPullMessageList(PullMessage pullMessage) {
        return pullMessageMapper.selectPullMessageList(pullMessage);
    }

    /**
     * 新增推送消息
     *
     * @param pullMessage 推送消息信息
     * @return 结果
     */
    @Override
    public int insertPullMessage(PullMessage pullMessage) {
        return pullMessageMapper.insertPullMessage(pullMessage);
    }

    /**
     * 修改推送消息
     *
     * @param pullMessage 推送消息信息
     * @return 结果
     */
    @Override
    public int updatePullMessage(PullMessage pullMessage) {
        return pullMessageMapper.updatePullMessage(pullMessage);
    }

    /**
     * 删除推送消息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePullMessageByIds(String ids) {
        return pullMessageMapper.deletePullMessageByIds(Convert.toStrArray(ids));
    }

}
