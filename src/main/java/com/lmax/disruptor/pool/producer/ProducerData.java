package com.lmax.disruptor.pool.producer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.pool.event.DataConsumerCallback;
import com.lmax.disruptor.pool.event.ProducerCallback;
import com.lmax.disruptor.pool.event.TranslatorData;
import com.lmax.disruptor.pool.status.ProduceStatus;

public class ProducerData {
    private RingBuffer<TranslatorData> ringBuffer;

    private ProduceStatus status;

    private ProducerCallback callback;

    public ProducerData(RingBuffer<TranslatorData> ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.status = ProduceStatus.USED;
    }

    public void onData(DataConsumerCallback dataConsumerCallback){
        long sequence = ringBuffer.next();
        try {
            TranslatorData translatorData = ringBuffer.get(sequence);
            translatorData.setCallback(dataConsumerCallback);
        }finally {
            ringBuffer.publish(sequence);
            callback.release(this);
        }

    }

    public ProduceStatus getStatus() {
        return status;
    }

    public void setStatus(ProduceStatus status) {
        this.status = status;
    }

    public ProducerCallback getCallback() {
        return callback;
    }

    public void setCallback(ProducerCallback callback) {
        this.callback = callback;
    }
}
