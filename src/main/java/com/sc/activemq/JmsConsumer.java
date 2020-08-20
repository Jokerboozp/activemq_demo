package com.sc.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

    public static final String ACTIVEMQ_URL="tcp://192.168.64.128:61616";
    public static final String QUEUE_NAME="queue01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        //创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
       /* while (true){
            //TextMessage textMessage = (TextMessage) messageConsumer.receive();一直等待
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);//有等待时间，4秒钟以后停止等待
            if (null != textMessage){
                System.out.println("*********消费者接收消息："+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null !=message && message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage) message;
                    try {
                        System.out.println("*******消费者接收到消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
