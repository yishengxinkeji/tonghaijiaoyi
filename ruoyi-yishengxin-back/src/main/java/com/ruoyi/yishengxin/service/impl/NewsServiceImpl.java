package com.ruoyi.yishengxin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yishengxin.mapper.NewsMapper;
import com.ruoyi.yishengxin.domain.News;
import com.ruoyi.yishengxin.service.INewsService;
import com.ruoyi.common.support.Convert;

/**
 * 新闻资讯 服务层实现
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Service
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 查询新闻资讯信息
     *
     * @param id 新闻资讯ID
     * @return 新闻资讯信息
     */
    @Override
    public News selectNewsById(Integer id) {
        return newsMapper.selectNewsById(id);
    }

    /**
     * 查询新闻资讯列表
     *
     * @param news 新闻资讯信息
     * @return 新闻资讯集合
     */
    @Override
    public List<News> selectNewsList(News news) {
        return newsMapper.selectNewsList(news);
    }

    /**
     * 新增新闻资讯
     *
     * @param news 新闻资讯信息
     * @return 结果
     */
    @Override
    public int insertNews(News news) {
        return newsMapper.insertNews(news);
    }

    /**
     * 修改新闻资讯
     *
     * @param news 新闻资讯信息
     * @return 结果
     */
    @Override
    public int updateNews(News news) {
        return newsMapper.updateNews(news);
    }

    /**
     * 删除新闻资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNewsByIds(String ids) {
        return newsMapper.deleteNewsByIds(Convert.toStrArray(ids));
    }

    @Override
    public int selectTotal() {
        return newsMapper.selectTotal();
    }
}
