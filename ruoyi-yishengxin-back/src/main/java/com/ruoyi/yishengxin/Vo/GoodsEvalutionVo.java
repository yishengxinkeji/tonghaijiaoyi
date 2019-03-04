package com.ruoyi.yishengxin.Vo;

import java.util.Date;
import java.util.List;

public class GoodsEvalutionVo {

    /** 商品评价id */
    private Integer id;
    /** 用户的id */
    private Integer uid;
    /** 商品的id */
    private Integer gid;
    /** 订单的id */
    private Integer oid;
    /** 评价内容 */
    private String evaluationContent;
    /** 评价图片 */
    private List<String> evaluationImage;
    /** 描述评价 */
    private Integer describeEvaluation;
    /** 物流评价 */
    private Integer logisticsEvaluation;
    /** 服务态度 */
    private Integer serviceAttitude;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public List<String> getEvaluationImage() {
        return evaluationImage;
    }

    public void setEvaluationImage(List<String> evaluationImage) {
        this.evaluationImage = evaluationImage;
    }

    public Integer getDescribeEvaluation() {
        return describeEvaluation;
    }

    public void setDescribeEvaluation(Integer describeEvaluation) {
        this.describeEvaluation = describeEvaluation;
    }

    public Integer getLogisticsEvaluation() {
        return logisticsEvaluation;
    }

    public void setLogisticsEvaluation(Integer logisticsEvaluation) {
        this.logisticsEvaluation = logisticsEvaluation;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
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

}
