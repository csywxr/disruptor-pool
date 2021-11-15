package com.lmax.disruptor.pool.event;

public class TranslatorData {


    private DataConsumerCallback callback;

    public DataConsumerCallback getCallback() {
        return callback;
    }

    public void setCallback(DataConsumerCallback callback) {
        this.callback = callback;
    }
}
