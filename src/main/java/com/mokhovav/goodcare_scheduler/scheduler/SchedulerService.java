package com.mokhovav.goodcare_scheduler.scheduler;

import com.mokhovav.goodcare_scheduler.SQL.DBService;
import com.mokhovav.goodcare_scheduler.entites.ExecuteType;
import com.mokhovav.goodcare_scheduler.entites.Task;
import com.mokhovav.goodcare_scheduler.exceptions.GoodCareException;
import com.mokhovav.goodcare_scheduler.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulerService {
    @Autowired
    private TaskScheduler scheduler;
    @Autowired
    private DBService dbService;
    @Autowired
    private Logger logger;
    @Autowired
    private Schedulers schedulers;
    @Autowired
    private ApplicationContext context;

    private void addNewTask(Task task) {
        if (task == null || task.getId() == 0L) logger.error("Task is Empty");

        if (task.isRunning()) {
            logger.info("The \"" + task.getName() + "\" task is still running");
            return;
        }

        long currentTime = new Date().getTime();
        Timestamp time = task.getStopTime();

        if (time != null && currentTime >= time.getTime()) {
            logger.info("The \"" + task.getName() + "\" task has timed out");
            return;
        }

        long startTime = task.getStartTime().getTime();
        long interval = task.getTimeInterval();
        if (task.getExecuteType() == ExecuteType.LAZY && startTime <= currentTime)
            task.setRunTime(new Timestamp(
                    startTime + ((currentTime - startTime)/interval + 1)*interval
            ));
        else
            task.setRunTime(task.getStartTime());

        SchedulerTask schedulerTask = context.getBean(SchedulerTask.class);
        schedulerTask.setTask(task);

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                schedulerTask,
                task.getRunTime(),
                task.getTimeInterval()
        );
        if (scheduledFuture != null) schedulers.put(task.getId(), scheduledFuture);
        logger.info("New task is added. Number of threads: " + schedulers.size());
    }

    public void createTasksFromDBonStartUP() {
        List<Task> tasks = (List<Task>) dbService.getAll(Task.class);
        if (tasks != null)
            for (Task task : tasks) {
                task.setRunning(false);
                task.setExecuted(false);
                addNewTask(task);
            }
    }
}
