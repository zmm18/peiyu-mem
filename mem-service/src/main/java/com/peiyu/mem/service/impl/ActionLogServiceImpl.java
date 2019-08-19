package com.peiyu.mem.service.impl;

import com.google.gson.Gson;
import com.peiyu.mem.domian.entity.ActionLog;
import com.peiyu.mem.rabbitmq.produces.MqSenderHandler;
import com.peiyu.mem.service.ActionLogService;
import com.peiyu.mem.utils.ParamUtils;
import javassist.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/20.
 */
@Service
public class ActionLogServiceImpl implements ActionLogService {
    @Autowired
    private MqSenderHandler senderHandler;

    private final Gson gson = new Gson();

    @Override
    public Object insertActionLog(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NotFoundException {
        return actionLog(joinPoint, 0);
    }

    @Override
    public Object deleteActionLog(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NotFoundException {
        return actionLog(joinPoint, 1);
    }

    @Override
    public Object updateActionLog(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NotFoundException {
        return actionLog(joinPoint, 2);
    }

    @Override
    public Object getActionLog(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NotFoundException {
        return actionLog(joinPoint, 3);
    }


    /**
     * 公共处理操作日志
     *
     * @param joinPoint
     * @param type
     * @return
     */
    protected Object actionLog(ProceedingJoinPoint joinPoint, int type) throws ClassNotFoundException, NotFoundException {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        StringBuilder methodParam = new StringBuilder();
        StringBuilder pavamValues = new StringBuilder();
        if (!className.contains("dao")) {
            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String[] methodParams;
            methodParams = ParamUtils.getPavamsName(clazz, clazzName, methodName);
            if (methodParams != null && methodParams.length > 0) {
                for (String str : methodParams) {
                    methodParam.append(str + ",");
                }
            }
        }
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object obj : args) {
                String typeName = obj.getClass().getName();
                if (ParamUtils.isBasicType(typeName)) {
                    pavamValues.append(obj + ",");
                } else {
                    pavamValues.append(ParamUtils.getFieldsValue(obj) + ",");
                }
            }
        }
        ActionLog actionLog = new ActionLog();
        actionLog.setVendorId(0l);
        actionLog.setMemNo("0");
        actionLog.setClassName(className);
        actionLog.setMethodName(methodName);
        actionLog.setMethodType(type);
        actionLog.setCreateDate(new Date());
        actionLog.setMethodParam(methodParam.toString());
        actionLog.setParamValue(pavamValues.toString());
        long start = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        actionLog.setOperationTime(end - start);
        String data = gson.toJson(actionLog);
        senderHandler.sendMessage("spring.actionLog.queueKey", data);
        return obj;
    }
}
