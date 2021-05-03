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
 * @since 2021-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MoneyDetail对象", description="")
public class MoneyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "转账详情主键")
    @TableId(value = "money_detail_id", type = IdType.AUTO)
    private Integer moneyDetailId;

    @ApiModelProperty(value = "金额主键")
    private Integer moneyId;

    @ApiModelProperty(value = "收入或支出")
    private String type;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "源")
    private String toFor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public String toString() {
        return "MoneyDetail{" +
                "moneyDetailId=" + moneyDetailId +
                ", moneyId=" + moneyId +
                ", type='" + type + '\'' +
                ", money=" + money +
                ", toFor='" + toFor + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getToFor() {
        return toFor;
    }

    public void setToFor(String toFor) {
        this.toFor = toFor;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMoneyDetailId() {
        return moneyDetailId;
    }

    public void setMoneyDetailId(Integer moneyDetailId) {
        this.moneyDetailId = moneyDetailId;
    }

    public Integer getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Integer moneyId) {
        this.moneyId = moneyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
