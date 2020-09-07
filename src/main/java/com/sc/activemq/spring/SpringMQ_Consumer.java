package com.sc.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMQ_Consumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringMQ_Consumer consumer = (SpringMQ_Consumer) ctx.getBean("springMQ_Consumer");

        String retValue = (String) consumer.jmsTemplate.receiveAndConvert();

        System.out.println("********消费者收到的消息:" + retValue);
    }
}
