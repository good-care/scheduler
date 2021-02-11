package com.mokhovav.goodcare_scheduler.scheduler;

import com.mokhovav.goodcare_scheduler.SQL.DBService;
import com.mokhovav.goodcare_scheduler.entites.Task;
import com.mokhovav.goodcare_scheduler.entites.TaskType;
import com.mokhovav.goodcare_scheduler.logger.Logger;
import com.mokhovav.goodcare_scheduler.rabbitMQ.RMQMessage;
import com.mokhovav.goodcare_scheduler.rabbitMQ.RMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
@DependsOn({"schedulers"})
@Scope("prototype")
public class SchedulerTask implements Runnable {
    private Task task = null;
    private ScheduledFuture<?> scheduler = null;
    @Autowired
    private RMQService rmqService;
    @Autowired
    private Logger logger;
    @Autowired
    private DBService dbService;
    @Autowired
    private Schedulers schedulers;

    public SchedulerTask() {
    }

    public void SchedulerTaskInit() {
        task.setRunning(true);
        dbService.update(task);

        scheduler = schedulers.get(task.getId());
        while (scheduler == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduler = schedulers.get(task.getId());
        }
    }

    @Override
    public void run() {
        if (scheduler == null)
            SchedulerTaskInit();
        if (task.isExecuted()){
            logger.info("The intervals for \"" + task.getName() + "\" are too short");
            return;
        }
        task.setExecuted(true);

        // Checking if it's time to stop
        long currentTime = new Date().getTime();
        Timestamp stopTime = task.getStopTime();
        if (stopTime != null && currentTime >= stopTime.getTime()) {
            schedulers.remove(task.getId());
            task.setRunning(false);
            dbService.update(task);
            logger.info("The \"" + task.getName() + "\" task is finished. Number of threads: " + schedulers.size());
            task.setExecuted(false);
            task.setRunning(false);
            dbService.update(task);
            scheduler.cancel(true);
            return;
        }

        // Task executing
        try {
            taskExecute(task);
            task.setLastStartTime(new Timestamp(currentTime));
            task.setLastSuccess(true);
        } catch (Exception e){
            task.setLastSuccess(false);
        }
        // The end of executing
        task.setExecuted(false);
        dbService.update(task);
    }

    private void taskExecute(Task task){
        switch (task.getTaskType()) {
            case UPDATE_QUOTATIONS:
                updateSQuotationsTask();
                break;
            case UPDATE_SECURITIES:
                updateSecuritiesTask();
                break;
            default:
                logger.info("Unknown task : " + task.getTaskType());
        }
    }

    private void updateSecuritiesTask() {
        logger.info("Update securities task is sending");
        rmqService.send(new RMQMessage(TaskType.UPDATE_SECURITIES, new String[]{}));
    }

    private void updateSQuotationsTask() {
        logger.info("UPDATE_QUOTATIONS");
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
            }
}
