package com.lkm.accenture;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lkm.accenture.service.EmployeeServiceReceiverImpl;

@SpringBootApplication
public class Application {

	// Message listener should be listening to the same queue as mentioned in the producer 
    final static String queueName = "dummy-msd-queue";

    // MessageListenerAdapter is used to invoke the receivers/services specified method to service the in coming payload/message 
    @Bean
    MessageListenerAdapter listenerAdapter(EmployeeServiceReceiverImpl receiver) {
        System.out.println("FRom Application:> Created Receiver Adapator: "+receiver);
    	return new MessageListenerAdapter(receiver, "receiveMessage"); // it is telling which method is responsible for consuming the message
    }
    
    
    // MessageListenerAdapator created in previous step is given to a container
    // where it will be linked to a RabbitMQ Connectionfactory and QueueName
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
    										 MessageListenerAdapter fec) {
    	System.out.println("FRom Application:> SimpleMessageListenerContainer: "+connectionFactory +"Adaptor is: ["+fec+"]");
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(fec);
        return container;
    }
    

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}
