package com.mokhovav.goodcare_scheduler.rabbitMQ;

import com.mokhovav.goodcare_scheduler.exceptions.GoodCareException;
import com.mokhovav.goodcare_scheduler.logger.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Logger logger;

    @Bean
    public SchedulerToMOEXInfoQueue toMOEXInfoQueue(){
        return new SchedulerToMOEXInfoQueue("SchedulerToMOEXInfo",false);
    }

    @Bean
    public MOEXInfoToSchedulerQueue toSchedulerQueue() {
        return new MOEXInfoToSchedulerQueue("MOEXInfoToScheduler", false);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    public void send(RMQMessage message){
        rabbitTemplate.convertAndSend("SchedulerToMOEXInfo", message);
    }

    @RabbitListener(queues = "MOEXInfoToScheduler")
    public void listen(GoodCareException in) {
        logger.error(in.getMessage());
    }

}
