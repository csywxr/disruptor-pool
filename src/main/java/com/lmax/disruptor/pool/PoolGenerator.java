package com.lmax.disruptor.pool;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.pool.consumer.ConsumerData;
import com.lmax.disruptor.pool.event.ProducerCallback;
import com.lmax.disruptor.pool.event.TranslatorData;
import com.lmax.disruptor.pool.producer.ProducerData;
import com.lmax.disruptor.pool.status.ProduceStatus;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PoolGenerator {
    private static PoolGenerator INSTANCE = new PoolGenerator();

    private LinkedBlockingQueue<ProducerData> producers;

    private ConsumerData[] consumers;

    private PoolConfig poolConfig;

    private RingBuffer<TranslatorData> ringBuffer;
    private PoolGenerator (){
        init();
    }

    public static PoolGenerator getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void init(){
        this.poolConfig = new PoolConfig();
        this.producers = new LinkedBlockingQueue<ProducerData>(poolConfig.getProducerSize());
        this.consumers = new ConsumerData[poolConfig.getConsumerSize()];

    }

    public void start(int bufferSize, WaitStrategy waitStrategy){
        this.ringBuffer =RingBuffer.create(ProducerType.MULTI,
                        new EventFactory<TranslatorData>() {
                            @Override
                            public TranslatorData newInstance() {
                                return new TranslatorData();
                            }
                        },bufferSize,waitStrategy);
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        for(int i=0;i<consumers.length;i++){
            consumers[i] = new ConsumerData();
        }
        WorkerPool<TranslatorData> workerPool =
                new WorkerPool<TranslatorData>(ringBuffer,sequenceBarrier,new EventExceptionHandler(),consumers);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(new CustomThreadPoolExecutor(poolConfig.getMinThreads(),poolConfig.getMaxThreads(),
                poolConfig.getQueueSize()));
        for(int i=0;i<poolConfig.getInitalProducerSize();i++){
            try {
                ProducerData producerData =new ProducerData(this.ringBuffer);
                producerData.setCallback(new ProducerCallback() {
                    @Override
                    public void release(ProducerData producer) {
                        try {
                            returnObject(producer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                this.producers.put(producerData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private static class SingletonHolder {
        private static final PoolGenerator INSTANCE = new PoolGenerator();
    }

    public ProducerData poll(){
        ProducerData producerData = null;
        try {
            producerData = producers.poll(2l, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }
        if(producerData == null){
            producerData = new ProducerData(this.ringBuffer);
            producerData.setCallback(new ProducerCallback() {
                @Override
                public void release(ProducerData producer) {
                    try {
                        returnObject(producer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return producerData;
    }

    public void returnObject( ProducerData producerData) throws Exception {
        producerData.setStatus(ProduceStatus.INVALID);
        producers.put(producerData);
    }


    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
