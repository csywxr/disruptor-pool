package com.lmax.disruptor.pool.event;

import com.lmax.disruptor.pool.producer.ProducerData;

public interface ProducerCallback {

    void release(ProducerData producer);
}
