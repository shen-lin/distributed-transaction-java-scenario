package shenlin84.katacoda.scenarios.distributedtransactionjavascenario;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.standalone.ConsumerService;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.standalone.TxConsumerService;

@ComponentScan
public class ConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConsumerApplication.class, args);
        ConsumerService bean = ctx.getBean(ConsumerService.class);
        bean.test();
        // DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        // // Specify name server addresses.
        // consumer.setNamesrvAddr("localhost:9876");

        // // Subscribe one more more topics to consume.
        // consumer.subscribe("Test", "transfer");
        // // Register callback to execute on arrival of messages fetched from brokers.
        // consumer.registerMessageListener(new MessageListenerConcurrently() {

        // @Override
        // public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
        // ConsumeConcurrentlyContext context) {
        // System.out.printf("%s Receive New Messages: %s %n",
        // Thread.currentThread().getName(), msgs);
        // // return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        // return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        // }
        // });

        // consumer.start();
    }
}
