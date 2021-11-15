package com.lmax.disruptor.pool;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.pool.event.TranslatorData;

public class EventExceptionHandler implements ExceptionHandler<TranslatorData> {
    @Override
    public void handleEventException(Throwable throwable, long l, TranslatorData translatorData) {

    }

    @Override
    public void handleOnStartException(Throwable throwable) {

    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {

    }
}
