package org.eventbuilder.model;

import com.google.gson.JsonObject;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SubscriberImpl implements Subscriber {
    private String brokerUrl;
    private EventStore eventStore;

    public SubscriberImpl(String brokerUrl, EventStore eventStore) {
        this.brokerUrl = brokerUrl;
        this.eventStore = eventStore;
    }

    @Override
    public void subscribe(String topicName) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);

            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            String text = ((TextMessage) message).getText();
                            JsonObject json = new JsonObject();
                            json.addProperty("message", text);
                            eventStore.saveEvent(topicName, json);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
