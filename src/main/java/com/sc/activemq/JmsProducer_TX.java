package com.sc.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_TX {

    public static final String ACTIVEMQ_URL = "tcp://192.168.64.128:61616";
    public static final String QUEUE_NAME = "tx-message";

    public static void main(String[] args) throws JMSException {
        //创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //通过连接工厂获得连接Connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //创建会话session
        //两个参数：第一个叫事务，第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);

        //创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        //设置消息非持久化
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //通过使用消息生产者生产3条消息发送到mq队列中
        for (int i = 1; i <= 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("tx msg----:" + i);//创建一个字符串
            //通过消息生产者发送给mq
            messageProducer.send(textMessage);
        }

        //关闭资源
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();

        System.out.println("tx消息发送到mq完成");

    }
}
