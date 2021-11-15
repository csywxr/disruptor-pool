package com.lmax.disruptor.pool.consumer;

import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.pool.event.TranslatorData;

public class ConsumerData implements WorkHandler<TranslatorData> {

    public void onEvent(TranslatorData translatorData) throws Exception {
        translatorData.getCallback().consume();
    }
}
