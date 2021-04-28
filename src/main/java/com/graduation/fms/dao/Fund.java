package com.graduation.fms.dao;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Fund对象", description="")
public class Fund implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "基金编号")
    @TableId(value = "fund_id", type = IdType.AUTO)
    private Integer fundId;

    @ApiModelProperty(value = "基金名字")
    private String fundName;

    @ApiModelProperty(value = "基金风险程度")
    private String fundRiskLevel;

    @ApiModelProperty(value = "基金收益能力")
    private String earning;

    @ApiModelProperty(value = "基金抗风险波动")
    private String antiRisk;

    @ApiModelProperty(value = "基金投资性价比")
    private String costPerformance;

    @ApiModelProperty(value = "基金规模")
    private BigDecimal fundMoney;

    @ApiModelProperty(value = "基金公司")
    private String fundCompany;

    @ApiModelProperty(value = "基金经理")
    private String fundManager;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "是否推送")
    private String isPush;

    @Override
    public String toString() {
        return "Fund{" +
                "fundId=" + fundId +
                ", fundName='" + fundName + '\'' +
                ", fundRiskLevel='" + fundRiskLevel + '\'' +
                ", earning='" + earning + '\'' +
                ", antiRisk='" + antiRisk + '\'' +
                ", costPerformance='" + costPerformance + '\'' +
                ", fundMoney=" + fundMoney +
                ", fundCompany='" + fundCompany + '\'' +
                ", fundManager='" + fundManager + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isPush='" + isPush + '\'' +
                '}';
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundRiskLevel() {
        return fundRiskLevel;
    }

    public void setFundRiskLevel(String fundRiskLevel) {
        this.fundRiskLevel = fundRiskLevel;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getAntiRisk() {
        return antiRisk;
    }

    public void setAntiRisk(String antiRisk) {
        this.antiRisk = antiRisk;
    }

    public String getCostPerformance() {
        return costPerformance;
    }

    public void setCostPerformance(String costPerformance) {
        this.costPerformance = costPerformance;
    }

    public BigDecimal getFundMoney() {
        return fundMoney;
    }

    public void setFundMoney(BigDecimal fundMoney) {
        this.fundMoney = fundMoney;
    }

    public String getFundCompany() {
        return fundCompany;
    }

    public void setFundCompany(String fundCompany) {
        this.fundCompany = fundCompany;
    }

    public String getFundManager() {
        return fundManager;
    }

    public void setFundManager(String fundManager) {
        this.fundManager = fundManager;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
