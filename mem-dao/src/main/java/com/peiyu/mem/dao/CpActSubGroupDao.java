package com.peiyu.mem.dao;

import com.peiyu.mem.domian.entity.CpActsubGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 900045
 * Created by Administrator on 2016/11/30.
 */
@Repository
public interface CpActSubGroupDao extends BaseDao<CpActsubGroup> {
    /**
     * 获取优惠券组列表
     *
     * @param search
     * @return
     */
    List<CpActsubGroup> getCpActsubGroupList(CpActsubGroup search);

    /**
     * 获取批量的活动编号获取批量的优惠券组
     *
     * @param
     * @return
     */
    List<CpActsubGroup> getCpActsubGroupsByActNos(@Param("vendorId") Long vendorId,
                                                  @Param("actNos") List<String> actNos);
}
