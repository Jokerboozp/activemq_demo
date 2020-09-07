package com.sc.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://192.168.64.128:61616";
    public static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("*********z3");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark");

        //创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("**********收到的持久化topic:" + textMessage.getText());
            message = topicSubscriber.receive(1000L);
        }

        session.close();
        connection.close();
    }
}
