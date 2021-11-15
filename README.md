Disruptor是英国外汇交易公司LMAX开发的一个高性能队列，实际使用中直接写disruptor那套写法还是不太简易和高效。
disruptor-pool提供多生产者和多消费者的disruptor设计模式。为进一步提供性能引入池化的概念且也更易使用。
使用示例：
 PoolGenerator instance = PoolGenerator.getInstance();
        instance.init();
        instance.start(1024*1024, new YieldingWaitStrategy());
        ProducerData producerData = instance.poll();
        producerData.onData(new DataConsumerCallback() {
            @Override
            public void consume() {
                System.out.println("sdadsa");
            }
        });
