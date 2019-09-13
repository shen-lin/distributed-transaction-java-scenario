package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Service
@PropertySource("classpath:pool.config.properties")
public class RmqTxProducerPoolConfig extends GenericObjectPoolConfig {

    @Autowired
    public RmqTxProducerPoolConfig(@Value("${max.idle}") int maxIdle, @Value("${max.total}") int maxTotal,
            @Value("${test.onborrow}") boolean testOnBorrow, @Value("${test.onreturn}") boolean testOnReturn) {
        this.setMaxIdle(maxIdle);
        this.setMaxTotal(maxTotal);
        this.setTestOnBorrow(testOnBorrow);
        this.setTestOnReturn(testOnReturn);
    }
}