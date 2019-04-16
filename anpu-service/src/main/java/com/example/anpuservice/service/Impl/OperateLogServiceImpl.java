package com.example.anpuservice.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.example.anpuservice.bo.MagOperateLogReqVO;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.mapper.ManagerOperateLogMapper;
import com.example.anpuservice.model.ManagerOperateLog;
import com.example.anpuservice.model.ManagerRoleInfo;
import com.example.anpuservice.service.ManagerMenuInfoService;
import com.example.anpuservice.service.OperateLogService;
import com.example.anpuservice.service.RoleInfoService;
import com.example.anpuservice.service.UserInfoService;
import com.example.anpuservice.utils.DateUtils;
import com.example.anpuservice.utils.PoiExcelUtils;
import com.example.anpuservice.vo.ManagerMenuInfoVO;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import com.example.anpuservice.vo.TreeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 17:09
 */


@Slf4j
@Service
public class OperateLogServiceImpl extends BaseServiceImpl<ManagerOperateLogMapper, ManagerOperateLog> implements OperateLogService {


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private ManagerMenuInfoService managerMenuInfoService;



    @Override
    public List<TreeVO> getOperateNameTreeList() {
        List<TreeVO> treeVOList = mapper.getOperationNameList();
        return treeVOList;
    }

    @Override
    public PageInfo<ManagerOperateLog> findOperateLogList(MagOperateLogReqVO magOperateLogReqVO) {
        if (magOperateLogReqVO.getCurrentPage() != null && magOperateLogReqVO.getPageSize() != null) {
            PageHelper.startPage(magOperateLogReqVO.getCurrentPage(), magOperateLogReqVO.getPageSize());
        }
        String beginTime = magOperateLogReqVO.getBeginTime();
        if (StringUtils.hasText(beginTime)) {
            beginTime = DateUtils.getBeginTimeOrEndTime(beginTime, true);
            magOperateLogReqVO.setBeginTime(beginTime);
        }

        String endTime = magOperateLogReqVO.getEndTime();
        if (StringUtils.hasText(endTime)) {
            endTime = DateUtils.getBeginTimeOrEndTime(endTime, false);
            magOperateLogReqVO.setEndTime(endTime);
        }

        List<ManagerOperateLog> managerOperateLogList = mapper.selectOperateLogList(magOperateLogReqVO);
        return new PageInfo<>(managerOperateLogList);
    }

    @Override
    public void insertSelective(ManagerOperateLog managerOperateLog) {
        mapper.insertSelective(managerOperateLog);
    }

    @Override
    public void updateManagerOperateLog(String logNumber, Map<String, Object> responseMap) {
        Example example = new Example(ManagerOperateLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("logNumber", logNumber);
        List<ManagerOperateLog> list = mapper.selectByExample(example);
        if (list.size() > 0) {
            ManagerOperateLog magLog = list.get(0);
            String status = responseMap.get("status_code") + "";
            if ("200".equals(status)) {
                magLog.setResultStatus("1");
            } else {
                magLog.setResultStatus("0");
            }
            magLog.setResult(responseMap.toString());
            magLog.setUpdateTime(new Date());
            //若当前用户为空，则set新用户名
            if (StringUtils.isEmpty(magLog.getUserName())) {
                magLog.setUserName((String) responseMap.get("userName"));
            }

            mapper.updateByPrimaryKeySelective(magLog);
        }
    }

    @Override
    public void buildManagerOperateLog(ManagerOperateLog managerOperateLog, String paramValue, String methodName) {
        try {
            if (methodName != null && paramValue != null) {
                JSONObject beforeParams = new JSONObject();
                if (methodName.equalsIgnoreCase("updateProductRule")
                        || methodName.equalsIgnoreCase("updateProductRuleStatus")) {
                    //产品策略

                } else if (methodName.equalsIgnoreCase("updateProductModel")
                        || methodName.equalsIgnoreCase("updateProductModelStatus")) {

                } else if (methodName.equalsIgnoreCase("updateRiskRule")
                        || methodName.equalsIgnoreCase("updateRiskRuleStatus")) {

                } else if (methodName.equalsIgnoreCase("updateRiskModel")
                        || methodName.equalsIgnoreCase("updateRiskModelStatus")) {

                } else if (methodName.equalsIgnoreCase("updateCompany")) {

                } else if (methodName.equalsIgnoreCase("updateManager")
                        || methodName.equalsIgnoreCase("updateManagerStatusList")) {
                    //后台用户管理
                    List<ManagerUserInfoVO> managerUserInfoList = userInfoService.getManagerBeforeUserInfo(paramValue, methodName);
                    managerOperateLog.setBeforeParams(JSONObject.toJSONString(managerUserInfoList));
                } else if (methodName.equalsIgnoreCase("updateProduct")
                        || methodName.equalsIgnoreCase("updateProductInfoStatus")) {

                } else if (methodName.equalsIgnoreCase("addRoleToMenus")
                        || methodName.equalsIgnoreCase("updateRolePermInfo")) {
                    //角色管理
                    ManagerRoleInfo managerRoleInfo = roleInfoService.getRoleBeforeInfo(paramValue);
                    managerOperateLog.setBeforeParams(JSONObject.toJSONString(managerRoleInfo));
                } else if (methodName.equalsIgnoreCase("updateSystemConfigStatus")
                        || methodName.equalsIgnoreCase("updateSystemConfigInfo")) {

                } else if (methodName.equalsIgnoreCase("updateMenuInfo")
                        || methodName.equalsIgnoreCase("updateMenuInfoStatus")) {
                    //菜单管理
                    ManagerMenuInfoVO managerMenuInfoVO = managerMenuInfoService.getRiskManagerMenuBeforeInfo(paramValue);
                    managerOperateLog.setBeforeParams(JSONObject.toJSONString(managerMenuInfoVO));
                } else if (methodName.equalsIgnoreCase("updateProductChannel")) {

                } else if (methodName.equalsIgnoreCase("updateRiskBlackInfo")) {

                } else {

            }
            }
        } catch (Exception e) {

        }
    }


    @Override
    public List<TreeVO> getOperateTypeTreeList() {
        List<TreeVO> treeVOList = new ArrayList<>();
        treeVOList.add(new TreeVO(UserConstant.OPERATE_TYPE_INSERT, "新增"));
        treeVOList.add(new TreeVO(UserConstant.OPERATE_TYPE_DELETE, "删除"));
        treeVOList.add(new TreeVO(UserConstant.OPERATE_TYPE_UPDATE, "修改"));
        treeVOList.add(new TreeVO(UserConstant.OPERATE_TYPE_SELECT, "查询"));
        treeVOList.add(new TreeVO(UserConstant.OPERATE_TYPE_OTHER, "其他"));
        return treeVOList;
    }


    @Override
    public void exportOperateLogList(MagOperateLogReqVO magOperateLogReqVO, HttpServletResponse response) {
        //TODO
        PageInfo<ManagerOperateLog> managerOperateLogPageInfo = this.findOperateLogList(magOperateLogReqVO);
        List<Object[]> dataList = new ArrayList<>();
        String[] rowsName = new String[]{"日志ID", "操作人用户名", "操作名称", "操作类型", "方法名", "变更前参数", "请求参数", "请求ip", "操作结果状态码", "操作结果", "操作时间"};
        if (managerOperateLogPageInfo != null) {
            List<ManagerOperateLog> managerOperateLogList = managerOperateLogPageInfo.getList();
            if (managerOperateLogList != null && managerOperateLogList.size() > 0) {
                managerOperateLogList.stream().filter(Objects::nonNull).forEach(managerOperateLog -> {
                    Object[] objs = new Object[rowsName.length];
                    objs[0] = managerOperateLog.getId();
                    objs[1] = managerOperateLog.getUserName();
                    objs[2] = managerOperateLog.getOperation();
                    objs[3] = managerOperateLog.getOperateType();
                    objs[4] = managerOperateLog.getMethod();
                    objs[5] = managerOperateLog.getBeforeParams();
                    objs[6] = managerOperateLog.getParams();
                    objs[7] = managerOperateLog.getIp();
                    String resultStatus = managerOperateLog.getResultStatus();
                    if ("0".equals(resultStatus)) {
                        objs[8] = "失败";
                    } else if ("1".equals(resultStatus)) {
                        objs[8] = "成功";
                    } else {
                        objs[8] = "-";
                    }
                    objs[9] = managerOperateLog.getResult();
                    objs[10] = DateUtils.formatDate(managerOperateLog.getCreateTime(), DateUtils.YMD_HMS);
                    dataList.add(objs);
                });
            }
            PoiExcelUtils ex = new PoiExcelUtils("操作日志管理", rowsName, dataList);
            try {
                ex.export(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

}
