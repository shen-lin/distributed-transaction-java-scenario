package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.stereotype.Service;

@Service
public class RmqTxProducerFactory extends BasePooledObjectFactory<TransactionMQProducer> {
    @Override
    public TransactionMQProducer create() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");
        return producer;
    }

    @Override
    public PooledObject<TransactionMQProducer> wrap(TransactionMQProducer producer) {
        return new DefaultPooledObject<TransactionMQProducer>(producer);
    }

    @Override
    public void passivateObject(PooledObject<TransactionMQProducer> producer) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<TransactionMQProducer> producer) {
        return true;
    }
}