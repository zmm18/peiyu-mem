package com.peiyu.mem.service.impl;

import com.peiyu.mem.dao.BizCodeDao;
import com.peiyu.mem.domian.entity.BizCode;
import com.peiyu.mem.manager.BizCodeManager;
import com.peiyu.mem.service.BizService;
import com.peiyu.mem.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/5.
 */
@Service
public class BizServiceImpl implements BizService {
    private Logger log = Logger.getLogger(BizServiceImpl.class);
    @Autowired
    private BizCodeManager bizCodeManager;
    @Autowired
    private BizCodeDao bizCodeDao;
    @Override
    public synchronized String getOddNumbers(Long vendorId, String bno, Integer i) {
        String OddNumbers = null;
        //防止死循环
        if (i == null || i > 3) {
            return null;
        }
        try {
            String now = DateUtil.getFormatDateTime(new Date(), "yyyyMMdd");
            BizCode bizCode = bizCodeManager.getCodeByNo(vendorId, bno);
            if (bizCode != null) {
                String curDate = DateUtil.getFormatDateTime(bizCode.getCurDate(), "yyyyMMdd");
                if (now.equals(curDate)) {
                    bizCode.setSerialNumber(bizCode.getSerialNumber() + 1);
                } else {
                    bizCode.setCurDate(new Date());
                    bizCode.setSerialNumber(1);
                }
                bizCode.setModifyDate(new Date());
                OddNumbers = bizCode.getBizCode() + now + getSerialNumber(bizCode.getLengthValue(), bizCode.getSerialNumber());
                if (bizCodeDao.update(bizCode) <= 0) {
                    return this.getOddNumbers(vendorId, bno, i + 1);
                }
                bizCodeManager.updateCacheForBno(bizCode);
                return OddNumbers;
            }
        } catch (Exception e) {
            log.error("获取订单号失败：vendorId=" + vendorId + ",bno=" + bno, e);
            return OddNumbers;
        }
        return OddNumbers;
    }

    /**
     * 计算序号
     * @param serialNumberLength
     * @param start
     * @return
     */
    private synchronized String getSerialNumber(int serialNumberLength,int start) {
        for (int i = 0; i < serialNumberLength; i++) {
            Double db = Math.pow(10, i);
            if (start / db.intValue() == 0) {
                Double preD = Math.pow(10, serialNumberLength - i);
                String pre = String.valueOf(preD.intValue());
                pre = pre.substring(1, pre.length());
                return pre + start;
            }
        }
        return "00000";
    }
}
