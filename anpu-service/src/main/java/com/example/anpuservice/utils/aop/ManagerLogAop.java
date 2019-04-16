package com.example.anpuservice.utils.aop;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.model.ManagerOperateLog;
import com.example.anpuservice.service.OperateLogService;
import com.example.anpuservice.utils.IpUtil;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.annotation.ManagerLog;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * FileName: com.iyingke.riskbackstage.utils.aop.ManagerLogAop.java
 * Author: Administrator
 * Description:
 */
@Slf4j
@Aspect
@Component
public class ManagerLogAop {

    @Autowired
    private OperateLogService managerOperateLogService;

    private String logNumber;

    @Pointcut("@annotation(com.example.anpuservice.utils.annotation.ManagerLog)")
    public void ManagerLogPointCut() {
    }

    @Before("ManagerLogPointCut()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if (null == target) return;
        //参数
        String paramName = "";
        //操作说明
        String operation = "";

        String operateType = "";
        //方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = signature.getName();

        ManagerLog managerLog = method.getAnnotation(ManagerLog.class);
        if (null != managerLog) {
            paramName = managerLog.paramName();
            operation = managerLog.operation();
            operateType = managerLog.operateType();
        }
        ManagerOperateLog managerOperateLog = new ManagerOperateLog();
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //设置IP地址
        managerOperateLog.setIp(IpUtil.getRemoteIP(request));

        String className = joinPoint.getTarget().getClass().getName();
        managerOperateLog.setMethod(className + "." + methodName + "()");
        managerOperateLog.setOperation(operation);
        managerOperateLog.setOperateType(operateType);
        managerOperateLog.setCreateTime(new Date());
        managerOperateLog.setLogType("managerOperateLog");
        //方法整体所有入参值
        Object[] paramValues = joinPoint.getArgs();
        String paramValue = "";
        if (StringUtils.hasText(paramName)) {
            //多个paramName以，分割
            String[] paramIndex = paramName.split(",");

            for (int j = 0; j < paramIndex.length; j++) {
                //若paramName中存放的为下标(数字)
                if (paramIndex[j].matches("[0-9]+")) {
                    Integer index = Integer.valueOf(paramIndex[j]);
                    paramValue = paramValue + JSON.toJSONString(paramValues[index]) + " ";
                } else {
                    for (int i = 0; i < paramValues.length; i++) {
                        JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(paramValues[i]));
                        if (null != jsonObject.get(paramIndex[j])) {
                            paramValue = jsonObject.get(paramIndex[j]).toString();
                            break;
                        }
                    }
                }
            }
        }

        managerOperateLog.setParams(paramValue);

        //记录修改前的值
        managerOperateLogService.buildManagerOperateLog(managerOperateLog, paramValue, methodName);

        ManagerUserInfoVO managerUserInfoVO = ShiroUtils.getUserEntity();
        if (null != managerUserInfoVO) {
            managerOperateLog.setUserName(managerUserInfoVO.getAccountNumber());
        }
        //添加日志编号
        Random rd1 = new Random();
        logNumber = "magLogAspect" + System.currentTimeMillis() + rd1.nextInt(99999);
        managerOperateLog.setLogNumber(logNumber);

        managerOperateLogService.insertSelective(managerOperateLog);
    }

    @AfterThrowing(value = "ManagerLogPointCut()", throwing = "ex")
    public void afterThrowing(RiskBackStageException ex) {
        if (logNumber != null) {
            Map<String, Object> responseMap = new HashMap<>();
            //修复系统初始启动时@Before方法获取不到当前用户
            ManagerUserInfoVO managerUserInfoVO = ShiroUtils.getUserEntity();
            String userName = "";
            if (null != managerUserInfoVO) {
                userName = managerUserInfoVO.getAccountNumber();
            }
            responseMap.put("status_code", ex.getErrorCode());
            responseMap.put("result", ex.toString());
            responseMap.put("userName", userName);
            //更新结果字段
            managerOperateLogService.updateManagerOperateLog(logNumber, responseMap);
        }
    }

    @AfterReturning(value = "ManagerLogPointCut()", returning = "rvt")
    public void afterService(Object rvt) {
        if (logNumber != null) {
            Map<String, Object> responseMap = JSON.parseObject(JSON.toJSONString(rvt), Map.class);
            //修复系统初始启动时@Before方法获取不到当前用户
            ManagerUserInfoVO managerUserInfoVO = ShiroUtils.getUserEntity();
            String userName = "";
            if (responseMap != null) {
                if (null != managerUserInfoVO) {
                    userName = managerUserInfoVO.getAccountNumber();
                    responseMap.put("userName", userName);
                }
                //更新结果字段
                managerOperateLogService.updateManagerOperateLog(logNumber, responseMap);
            }

        }
    }
}
