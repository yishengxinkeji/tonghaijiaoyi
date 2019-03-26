package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.Notice;
import java.util.List;

/**
 * 公告中心 服务层
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
public interface INoticeService 
{
	/**
     * 查询公告中心信息
     * 
     * @param id 公告中心ID
     * @return 公告中心信息
     */
	public Notice selectNoticeById(Integer id);
	
	/**
     * 查询公告中心列表
     * 
     * @param notice 公告中心信息
     * @return 公告中心集合
     */
	public List<Notice> selectNoticeList(Notice notice);
	
	/**
     * 新增公告中心
     * 
     * @param notice 公告中心信息
     * @return 结果
     */
	public int insertNotice(Notice notice);
	
	/**
     * 修改公告中心
     * 
     * @param notice 公告中心信息
     * @return 结果
     */
	public int updateNotice(Notice notice);
		
	/**
     * 删除公告中心信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteNoticeByIds(String ids);

    int selectTotal();
}
