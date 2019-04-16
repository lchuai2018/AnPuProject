package com.example.anpuservice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "manager_menu_info")
public class ManagerMenuInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 菜单名
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单icon
     */
    private String icon;

    /**
     * 功能请求路径
     */
    @Column(name = "power_url")
    private String powerUrl;

    /**
     * 菜单类型：0目录，1菜单
     */
    @Column(name = "menu_type")
    private Integer menuType;

    /**
     * 是否使用状态（0：未使用   1：已使用）
     */
    private Integer status;

    /**
     * 菜单排序 数值越大，菜单越在前面
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 创建该菜单的用户ID
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
     * 获取父菜单ID，一级菜单为0
     *
     * @return parent_id - 父菜单ID，一级菜单为0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID，一级菜单为0
     *
     * @param parentId 父菜单ID，一级菜单为0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单名
     *
     * @return menu_name - 菜单名
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名
     *
     * @param menuName 菜单名
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单icon
     *
     * @return icon - 菜单icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单icon
     *
     * @param icon 菜单icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取功能请求路径
     *
     * @return power_url - 功能请求路径
     */
    public String getPowerUrl() {
        return powerUrl;
    }

    /**
     * 设置功能请求路径
     *
     * @param powerUrl 功能请求路径
     */
    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    /**
     * 获取菜单类型：0目录，1菜单
     *
     * @return menu_type - 菜单类型：0目录，1菜单
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型：0目录，1菜单
     *
     * @param menuType 菜单类型：0目录，1菜单
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
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
     * 获取菜单排序 数值越大，菜单越在前面
     *
     * @return order_num - 菜单排序 数值越大，菜单越在前面
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置菜单排序 数值越大，菜单越在前面
     *
     * @param orderNum 菜单排序 数值越大，菜单越在前面
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取创建该菜单的用户ID
     *
     * @return create_user_id - 创建该菜单的用户ID
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建该菜单的用户ID
     *
     * @param createUserId 创建该菜单的用户ID
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