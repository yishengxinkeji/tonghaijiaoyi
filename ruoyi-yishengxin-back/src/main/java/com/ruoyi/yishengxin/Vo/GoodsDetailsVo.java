package com.ruoyi.yishengxin.Vo;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 商品表 ysx_goods
 *
 * @author ruoyi
 * @date 2019-03-06
 */
public class GoodsDetailsVo
{
    /** 商品id */
    private Integer id;
    /** 商品名称 */
    private String goodsName;
    /** 商品介绍 */
    private String goodsIntroduce;
    /** 商品详情 */
    private String goodsDetails;
    /** 商品价格 */
    private String goodsPrice;
    /** 商品详情图 */
    private List<String> goodsDetailsPicture;
    /** 商品售出量 */
    private Integer goodsSoldNumber;
    /** 客服电话 */
    private String serviceTel;
    /** 商品图片 */
    private String goodsPicture;

    /**商品轮播图*/
    private List<String>  slideshow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<String> getGoodsDetailsPicture() {
        return goodsDetailsPicture;
    }

    public void setGoodsDetailsPicture(List<String> goodsDetailsPicture) {
        this.goodsDetailsPicture = goodsDetailsPicture;
    }

    public Integer getGoodsSoldNumber() {
        return goodsSoldNumber;
    }

    public void setGoodsSoldNumber(Integer goodsSoldNumber) {
        this.goodsSoldNumber = goodsSoldNumber;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public List<String> getSlideshow() {
        return slideshow;
    }

    public void setSlideshow(List<String> slideshow) {
        this.slideshow = slideshow;
    }
}
