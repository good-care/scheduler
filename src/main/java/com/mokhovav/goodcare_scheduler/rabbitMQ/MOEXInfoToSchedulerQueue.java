package com.mokhovav.goodcare_scheduler.rabbitMQ;

import org.springframework.amqp.core.Queue;

public class MOEXInfoToSchedulerQueue extends Queue {
    public MOEXInfoToSchedulerQueue(String name, boolean durable) {
        super(name, durable);
    }
}
