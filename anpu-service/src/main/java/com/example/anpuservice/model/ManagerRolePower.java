package com.example.anpuservice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "manager_role_power")
public class ManagerRolePower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色编号（功能被赋予给了那个角色）
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限编号
     */
    @Column(name = "power_id")
    private Integer powerId;

    /**
     * 是否使用状态（0：未使用  1：已使用）
     */
    private Integer status;

    /**
     * 创建人用户ID
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
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
     * 获取角色编号（功能被赋予给了那个角色）
     *
     * @return role_id - 角色编号（功能被赋予给了那个角色）
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号（功能被赋予给了那个角色）
     *
     * @param roleId 角色编号（功能被赋予给了那个角色）
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限编号
     *
     * @return power_id - 权限编号
     */
    public Integer getPowerId() {
        return powerId;
    }

    /**
     * 设置权限编号
     *
     * @param powerId 权限编号
     */
    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

    /**
     * 获取是否使用状态（0：未使用  1：已使用）
     *
     * @return status - 是否使用状态（0：未使用  1：已使用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否使用状态（0：未使用  1：已使用）
     *
     * @param status 是否使用状态（0：未使用  1：已使用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建人用户ID
     *
     * @return create_user_id - 创建人用户ID
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人用户ID
     *
     * @param createUserId 创建人用户ID
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}