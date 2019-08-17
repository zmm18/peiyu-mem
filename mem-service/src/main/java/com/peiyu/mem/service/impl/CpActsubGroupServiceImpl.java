package com.peiyu.mem.service.impl;

import com.peiyu.mem.commen.SysConstants;
import com.peiyu.mem.dao.CpActSubGroupDao;
import com.peiyu.mem.domian.entity.CpActsubGroup;
import com.peiyu.mem.domian.entity.CpApplyLimitdt;
import com.peiyu.mem.domian.entity.CpUseLimitdt;
import com.peiyu.mem.manager.CpActsubGroupManager;
import com.peiyu.mem.service.CpActsubGroupService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/3.
 */
@Service
public class CpActsubGroupServiceImpl implements CpActsubGroupService {
    private Logger log=Logger.getLogger(CpActivityServiceImpl.class);
    @Autowired
    private CpActSubGroupDao actSubGroupDao;
    @Autowired
    private CpActsubGroupManager actsubGroupManager;
    @Override
    public int insertActSubGroup(CpActsubGroup actSubGroup) {
        List<CpApplyLimitdt> applyLimitdtList = new ArrayList<>();
        List<CpUseLimitdt> useLimitdtList = new ArrayList<>();
        if (!actSubGroup.getApplyScopeType().equals(SysConstants.COUPONAPPLIEDRANGE.UNLIMITED)) {
            if (StringUtils.isEmpty(actSubGroup.getDetailCode())) {
                log.error("没有选择应用范围");
                return 0;
            }
        }
        if (!actSubGroup.getUseScopeType().equals(SysConstants.COUPONUSERANGE.UNLIMITED)) {
            if (StringUtils.isEmpty(actSubGroup.getOrganOrStoreCode())) {
                log.error("没有选择使用范围");
                return 0;
            }
        }
        String[] splitCodes = actSubGroup.getDetailCode().split(",");
        String[] splitNames = actSubGroup.getDetailName().split(",");
        for (int i = 0; i < splitCodes.length; i++) {
            CpApplyLimitdt applyLimit = new CpApplyLimitdt();
            BeanUtils.copyProperties(applyLimit, actSubGroup);
            applyLimit.setId(null);
            applyLimit.setOwnRecordType(SysConstants.OWNRECORDTYPE.GROUPS);
            applyLimit.setOwnRecordCode(actSubGroup.getSubgroupCode());
            applyLimit.setDetailCode(splitCodes[i]);
            applyLimit.setDetailName(splitNames[i]);
            applyLimitdtList.add(applyLimit);
        }
        String[] codes = actSubGroup.getOrganOrStoreCode().split(",");
        String[] names = actSubGroup.getOrganOrStoreName().split(",");
        for (int i = 0; i < codes.length; i++) {
            CpUseLimitdt useLimit = new CpUseLimitdt();
            BeanUtils.copyProperties(useLimit, actSubGroup);
            useLimit.setId(null);
            useLimit.setOwnRecordCode(actSubGroup.getSubgroupCode());
            useLimit.setOwnRecordType(SysConstants.OWNRECORDTYPE.GROUPS);
            if (actSubGroup.getUseScopeType().equals(SysConstants.COUPONUSERANGE.ORAGN)) {
                useLimit.setOrganCode(codes[i]);
                useLimit.setOrganName(names[i]);
            }
            if (actSubGroup.equals(SysConstants.COUPONUSERANGE.STORE)) {
                useLimit.setStoreCode(codes[i]);
                useLimit.setStoreName(names[i]);
            }
            useLimitdtList.add(useLimit);
        }
        if (actSubGroup.getId() == null) {
            if (actsubGroupManager.insertCpActSubGroup(actSubGroup, applyLimitdtList, useLimitdtList)) {
                return 1;
            }
        }
        if (actsubGroupManager.updateCpActsubGroup(actSubGroup, applyLimitdtList, useLimitdtList)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteActsubGroup(long id) {
        try {
            if (id != 0) {
                return actSubGroupDao.delete(id);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
        return 0;
    }

    @Override
    public int deleteBatchActsubGroup(String ids) {
        return 0;
    }

    @Override
    public int updateActSubGroup(CpActsubGroup search) {
        return actSubGroupDao.update(search);
    }

    @Override
    public CpActsubGroup getActSubGroup(Long id) {
        return actSubGroupDao.get(id);
    }
}
