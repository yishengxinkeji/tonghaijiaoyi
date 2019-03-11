package com.ruoyi.yishengxin.Vo;

import java.util.Date;

public class GoodsVo {
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
    private String goodsDetailsPicture;
    /** 商品库存 */
    private Integer goodsInventory;
    /** 商品售出量 */
    private Integer goodsSoldNumber;
    /** 商品上下架 */
    private String standUpAndDown;
    /** 客服电话 */
    private String serviceTel;
    /** 商品轮播图 */
    private String goodsSlideShow;
    /** 商品主图 */
    private String goodsMainFigure;
    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新者 */
    private String updateBy;
    /** 更新时间 */
    private Date updateTime;
    /** 备注 */
    private String remark;
    /**收藏 */
    private String collection;

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

    public String getGoodsDetailsPicture() {
        return goodsDetailsPicture;
    }

    public void setGoodsDetailsPicture(String goodsDetailsPicture) {
        this.goodsDetailsPicture = goodsDetailsPicture;
    }

    public Integer getGoodsInventory() {
        return goodsInventory;
    }

    public void setGoodsInventory(Integer goodsInventory) {
        this.goodsInventory = goodsInventory;
    }

    public Integer getGoodsSoldNumber() {
        return goodsSoldNumber;
    }

    public void setGoodsSoldNumber(Integer goodsSoldNumber) {
        this.goodsSoldNumber = goodsSoldNumber;
    }

    public String getStandUpAndDown() {
        return standUpAndDown;
    }

    public void setStandUpAndDown(String standUpAndDown) {
        this.standUpAndDown = standUpAndDown;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getGoodsSlideShow() {
        return goodsSlideShow;
    }

    public void setGoodsSlideShow(String goodsSlideShow) {
        this.goodsSlideShow = goodsSlideShow;
    }

    public String getGoodsMainFigure() {
        return goodsMainFigure;
    }

    public void setGoodsMainFigure(String goodsMainFigure) {
        this.goodsMainFigure = goodsMainFigure;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
