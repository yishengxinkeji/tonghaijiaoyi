package com.ruoyi.yishengxin.service;

import com.ruoyi.yishengxin.domain.News;
import java.util.List;

/**
 * 新闻资讯 服务层
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
public interface INewsService 
{
	/**
     * 查询新闻资讯信息
     * 
     * @param id 新闻资讯ID
     * @return 新闻资讯信息
     */
	public News selectNewsById(Integer id);
	
	/**
     * 查询新闻资讯列表
     * 
     * @param news 新闻资讯信息
     * @return 新闻资讯集合
     */
	public List<News> selectNewsList(News news);
	
	/**
     * 新增新闻资讯
     * 
     * @param news 新闻资讯信息
     * @return 结果
     */
	public int insertNews(News news);
	
	/**
     * 修改新闻资讯
     * 
     * @param news 新闻资讯信息
     * @return 结果
     */
	public int updateNews(News news);
		
	/**
     * 删除新闻资讯信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteNewsByIds(String ids);
	
}
