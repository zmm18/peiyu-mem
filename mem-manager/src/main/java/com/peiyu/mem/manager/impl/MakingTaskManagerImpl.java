package com.peiyu.mem.manager.impl;

import com.google.gson.Gson;
import com.peiyu.mem.dao.CpMakingTaskDao;
import com.peiyu.mem.domian.entity.CpMakingTask;
import com.peiyu.mem.manager.MakingTaskManager;
import com.peiyu.mem.redis.RedisTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/6.
 */
@Service
public class MakingTaskManagerImpl implements MakingTaskManager {
    private Logger log = Logger.getLogger(MakingTaskManagerImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CpMakingTaskDao makingTaskDao;
    private static Gson gson = new Gson();

    @Override
    public boolean isRepeat(CpMakingTask makingTask) {
        String taskCode = String.format("%s_%s", makingTask.getVendorId(), makingTask.getTaskCode());
        if (makingTask.getId() != null && !"".equals(makingTask.getId())) {
            CpMakingTask tempTask = makingTaskDao.get(makingTask.getId());
            if (tempTask != null) {
                redisTemplate.delete(taskCode);
            }
        }
        if (StringUtils.isNotBlank(redisTemplate.get(taskCode))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRepeatByMainKingCoupon(Long vendorId, String taskCode) {
        final String mainKingCode = String.format("MACK_COUPON_%s_%s", vendorId, taskCode);
        if (StringUtils.isNotBlank(redisTemplate.get(mainKingCode))) {
            return true;
        }
        return false;
    }

    @Override
    public void insertCacheByTaskCode(CpMakingTask makingTask) {
        String taskCode = String.format("%s_%s", makingTask.getVendorId(), makingTask.getTaskCode());
        String taskToJson = gson.toJson(makingTask);
        redisTemplate.set(taskCode, taskToJson,24*60*60);
    }

    @Override
    public void insertCacheByMainKingCoupon(Long vendorId, String taskCode) {
        final String mainKingCode=String.format("MACK_COUPON_%s_%s",vendorId,taskCode);
        redisTemplate.set(mainKingCode,"制券中...",24*60*60);
    }

    @Override
    public void deleteCacheByTaskCode(CpMakingTask makingTask) {
        String taskCode = String.format("%s_%s", makingTask.getVendorId(), makingTask.getTaskCode());
        redisTemplate.delete(taskCode);
    }

    @Override
    public void deleteCacheByMainKingCoupon(Long vendorId, String taskCode) {
        final String mainKingCode=String.format("MACK_COUPON_%s_%s",vendorId,taskCode);
        redisTemplate.delete(mainKingCode);
    }

    @Override
    public CpMakingTask getMakingTask(Long vendorId, String taskCode) {
        String taskCodeCache = String.format("%s_%s", vendorId, taskCode);
        String taskJson = redisTemplate.get(taskCodeCache);
        if (StringUtils.isNotBlank(taskJson)) {
            return gson.fromJson(taskJson, CpMakingTask.class);
        }
        return makingTaskDao.getCpMakingTaskByTaskCode(vendorId,taskCode);
    }
}
