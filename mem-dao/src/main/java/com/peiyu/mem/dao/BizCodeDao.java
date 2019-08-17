package com.peiyu.mem.dao;

import com.peiyu.mem.domian.entity.BizCode;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/5.
 * 系统单据号接口
 */
public interface BizCodeDao extends BaseDao<BizCode>{
    /**
     * 根据单据标识获取单据号
     * @param vendorId
     * @param bizNo
     * @return
     */
    BizCode getBizCodeByBno(@Param("vendorId") Long vendorId, @Param("bizNo") String bizNo);

}
