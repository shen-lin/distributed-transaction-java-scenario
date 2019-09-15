package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class RmqTxProducer {

    private RmqTxProducerListener transactionListener;
    private TransactionMQProducer producer;

    public RmqTxProducer(RmqTxProducerListener transactionListener) throws MQClientException {
        this.transactionListener = transactionListener;

        this.producer = new TransactionMQProducer("group1");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });
        this.producer.setExecutorService(executorService);
        this.producer.setTransactionListener(this.transactionListener);
        this.producer.setNamesrvAddr("localhost:9876");
    }

    public TransactionMQProducer getProducer() {
        return this.producer;
    }
}