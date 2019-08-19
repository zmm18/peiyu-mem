package com.peiyu.mem.rabbitmq.consumers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peiyu.mem.dao.CouponDao;
import com.peiyu.mem.domian.entity.Coupon;
import com.peiyu.mem.rabbitmq.Gson2JsonMessageConverter;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/12.
 */
@Component
public class MakeCouponsHandler2 implements ChannelAwareMessageListener {
    private Logger log = Logger.getLogger(MakeCouponsHandler2.class);
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private Gson2JsonMessageConverter jsonMessageConverter;

    private static Gson gson = new Gson();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            channel.basicQos(1);
            if (message == null || message.getBody() == null) {
                return;
            }
            String data = jsonMessageConverter.fromMessage(message).toString();
            if (StringUtils.isNotBlank(data)) {
                List<Coupon> coupons = gson.fromJson(data, new TypeToken<List<Coupon>>() {
                }.getType());
                couponDao.insertBatchCoupons(coupons);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
            log.error("消息队列处理制券异常：" + e);
        }
    }
}
