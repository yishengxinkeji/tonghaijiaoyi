package com.ruoyi.yishengxin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.NoticeMapper;
import com.ruoyi.yishengxin.domain.Notice;
import com.ruoyi.yishengxin.service.INoticeService;
import com.ruoyi.common.support.Convert;

/**
 * 公告中心 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Service
public class NoticeServiceImpl implements INoticeService 
{
	@Autowired
	private NoticeMapper noticeMapper;

	/**
     * 查询公告中心信息
     * 
     * @param id 公告中心ID
     * @return 公告中心信息
     */
    @Override
	public Notice selectNoticeById(Integer id)
	{
	    return noticeMapper.selectNoticeById(id);
	}
	
	/**
     * 查询公告中心列表
     * 
     * @param notice 公告中心信息
     * @return 公告中心集合
     */
	@Override
	public List<Notice> selectNoticeList(Notice notice)
	{
	    return noticeMapper.selectNoticeList(notice);
	}
	
    /**
     * 新增公告中心
     * 
     * @param notice 公告中心信息
     * @return 结果
     */
	@Override
	public int insertNotice(Notice notice)
	{
	    return noticeMapper.insertNotice(notice);
	}
	
	/**
     * 修改公告中心
     * 
     * @param notice 公告中心信息
     * @return 结果
     */
	@Override
	public int updateNotice(Notice notice)
	{
	    return noticeMapper.updateNotice(notice);
	}

	/**
     * 删除公告中心对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteNoticeByIds(String ids)
	{
		return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
	}
	
}
