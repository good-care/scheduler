package com.mokhovav.goodcare_scheduler.rabbitMQ;

import org.springframework.amqp.core.Queue;

public class SchedulerToMOEXInfoQueue extends Queue {
    public SchedulerToMOEXInfoQueue(String name, boolean durable) {
        super(name, durable);
    }
}
