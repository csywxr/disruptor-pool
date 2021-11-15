package com.lmax.disruptor.pool;

public class PoolConfig {

    private int producerSize = Integer.MAX_VALUE;

    private int consumerSize = 100;

    private int minThreads = 10;

    private int maxThreads = Integer.MAX_VALUE;

    private int queueSize =200;

    private int initalProducerSize = 5;

    public int getProducerSize() {
        return producerSize;
    }

    public void setProducerSize(int producerSize) {
        this.producerSize = producerSize;
    }

    public int getConsumerSize() {
        return consumerSize;
    }

    public void setConsumerSize(int consumerSize) {
        this.consumerSize = consumerSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getInitalProducerSize() {
        return initalProducerSize;
    }

    public void setInitalProducerSize(int initalProducerSize) {
        this.initalProducerSize = initalProducerSize;
    }
}
