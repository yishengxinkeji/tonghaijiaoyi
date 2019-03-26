package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Notice;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 公告中心 数据层
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Repository
public interface NoticeMapper {
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
     * 删除公告中心
     * 
     * @param id 公告中心ID
     * @return 结果
     */
	public int deleteNoticeById(Integer id);
	
	/**
     * 批量删除公告中心
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteNoticeByIds(String[] ids);

	@Select("select ifnull(count(*),0) from ysx_notice")
    int selectTotal();
}