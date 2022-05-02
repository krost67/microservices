package com.podlasenko.amqp;

public interface MessageProducer {
    void publish(Object payload, String exchange, String routingKey);
}
