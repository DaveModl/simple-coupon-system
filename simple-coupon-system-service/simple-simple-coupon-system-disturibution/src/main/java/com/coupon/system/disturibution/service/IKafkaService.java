package com.coupon.system.disturibution.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 *<h1> Kafka相关操作服务接口定义</h1>
 */
public interface IKafkaService {

    /**
     * <h2>消费优惠券 Kafka 消息</h2>
     *
     * @param record {@link ConsumerRecord}
     */
    void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record);
}
