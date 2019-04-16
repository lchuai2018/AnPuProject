package com.example.anpuservice.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "manager_user_role")
public class ManagerUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 创建管理员角色关系的管理员编号
     */
    @Column(name = "created_manager_id")
    private Integer createdManagerId;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 是否使用状态（0：未使用  1：使用）
     */
    private Integer status;

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
     * 获取创建管理员角色关系的管理员编号
     *
     * @return created_manager_id - 创建管理员角色关系的管理员编号
     */
    public Integer getCreatedManagerId() {
        return createdManagerId;
    }

    /**
     * 设置创建管理员角色关系的管理员编号
     *
     * @param createdManagerId 创建管理员角色关系的管理员编号
     */
    public void setCreatedManagerId(Integer createdManagerId) {
        this.createdManagerId = createdManagerId;
    }

    /**
     * 获取角色编号
     *
     * @return role_id - 角色编号
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取是否使用状态（0：未使用  1：使用）
     *
     * @return status - 是否使用状态（0：未使用  1：使用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否使用状态（0：未使用  1：使用）
     *
     * @param status 是否使用状态（0：未使用  1：使用）
     */
    public void setStatus(Integer status) {
        this.status = status;
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