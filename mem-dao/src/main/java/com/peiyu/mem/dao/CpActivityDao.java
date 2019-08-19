package com.peiyu.mem.dao;

import com.peiyu.mem.domian.entity.CpActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 900045
 * Created by Administrator on 2016/11/29.
 */
@Repository
public interface CpActivityDao extends BaseDao<CpActivity>{
    /**
     * 根据商家id获取优惠券活动
     * @param venorId
     * @return
     */
    List<CpActivity> getCpActivitysByVendorId(Long venorId);
    /**
     * 通过优惠券活动属性来查询优惠券活动
     * @param cpActivity
     * @return
     */
    List<CpActivity> getCpActivityBySearch(CpActivity cpActivity);
}
