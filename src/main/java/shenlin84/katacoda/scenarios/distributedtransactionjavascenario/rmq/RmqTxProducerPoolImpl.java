package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RmqTxProducerPoolImpl<TransactionMQProducer> extends GenericObjectPool<TransactionMQProducer>
        implements RmqTxProducerPool {

    @Autowired
    public RmqTxProducerPoolImpl(PooledObjectFactory<TransactionMQProducer> factory,
            GenericObjectPoolConfig<TransactionMQProducer> config) {
        super(factory, config);
    }
}