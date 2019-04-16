package com.example.anpuservice.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "manager_role_info")
public class ManagerRoleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否使用状态（0：未使用   1：已使用）
     */
    private Integer status;

    /**
     * 创建角色的管理用户编号
     */
    @Column(name = "created_manager_id")
    private Integer createdManagerId;

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
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取是否使用状态（0：未使用   1：已使用）
     *
     * @return status - 是否使用状态（0：未使用   1：已使用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否使用状态（0：未使用   1：已使用）
     *
     * @param status 是否使用状态（0：未使用   1：已使用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建角色的管理用户编号
     *
     * @return created_manager_id - 创建角色的管理用户编号
     */
    public Integer getCreatedManagerId() {
        return createdManagerId;
    }

    /**
     * 设置创建角色的管理用户编号
     *
     * @param createdManagerId 创建角色的管理用户编号
     */
    public void setCreatedManagerId(Integer createdManagerId) {
        this.createdManagerId = createdManagerId;
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