package com.mokhovav.goodcare_scheduler.rabbitMQ;

import com.mokhovav.goodcare_scheduler.entites.TaskType;

public class RMQMessage {
    private TaskType type;
    private String[] args;

    public RMQMessage() {
    }

    public RMQMessage(TaskType type, String[] args) {
        this.type = type;
        this.args = args;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
