package com.ruoyi.yishengxin.mapper;

import com.ruoyi.yishengxin.domain.Rotation;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 轮播图 数据层
 * 
 * @author ruoyi
 * @date 2019-03-12
 */
@Repository
public interface RotationMapper {
	/**
     * 查询轮播图信息
     * 
     * @param id 轮播图ID
     * @return 轮播图信息
     */
	public Rotation selectRotationById(Integer id);
	
	/**
     * 查询轮播图列表
     * 
     * @param rotation 轮播图信息
     * @return 轮播图集合
     */
	public List<Rotation> selectRotationList(Rotation rotation);
	
	/**
     * 新增轮播图
     * 
     * @param rotation 轮播图信息
     * @return 结果
     */
	public int insertRotation(Rotation rotation);
	
	/**
     * 修改轮播图
     * 
     * @param rotation 轮播图信息
     * @return 结果
     */
	public int updateRotation(Rotation rotation);
	
	/**
     * 删除轮播图
     * 
     * @param id 轮播图ID
     * @return 结果
     */
	public int deleteRotationById(Integer id);
	
	/**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRotationByIds(String[] ids);
	
}