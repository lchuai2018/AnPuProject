package com.example.anpuservice.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "manager_power_info")
public class ManagerPowerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限名称
     */
    @Column(name = "power_name")
    private String powerName;

    /**
     * 权限值
     */
    @Column(name = "power_key")
    private String powerKey;

    /**
     * 状态 0禁用 1可用
     */
    private Integer status;

    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限名称
     *
     * @return power_name - 权限名称
     */
    public String getPowerName() {
        return powerName;
    }

    /**
     * 设置权限名称
     *
     * @param powerName 权限名称
     */
    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    /**
     * 获取权限值
     *
     * @return power_key - 权限值
     */
    public String getPowerKey() {
        return powerKey;
    }

    /**
     * 设置权限值
     *
     * @param powerKey 权限值
     */
    public void setPowerKey(String powerKey) {
        this.powerKey = powerKey;
    }

    /**
     * 获取状态 0禁用 1可用
     *
     * @return status - 状态 0禁用 1可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0禁用 1可用
     *
     * @param status 状态 0禁用 1可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建人id
     *
     * @return create_user_id - 创建人id
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人id
     *
     * @param createUserId 创建人id
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}