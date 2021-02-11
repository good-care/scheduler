package com.mokhovav.goodcare_scheduler.entites;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "goodcare_scheduler_tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "start")
    private Timestamp startTime;
    @Column (name = "stop")
    private Timestamp stopTime;
    @Column (name = "interval")
    private Long timeInterval;
    @Column (name = "task_type")
    private TaskType taskType;
    @Column (name = "execute_type")
    private ExecuteType executeType;
    @Column (name = "last_time")
    private Timestamp lastStartTime;
    @Column (name = "is_success")
    private boolean isLastSuccess;
    @Column (name = "is_running")
    private boolean isRunning;
    @Transient
    private boolean isExecuted;
    @Transient
    private Timestamp runTime;
    @Column (name = "args")
    private String args;

    public Task() {
        lastStartTime = null;
        isLastSuccess = true;
        isRunning = false;
        isExecuted = false;
        executeType = ExecuteType.LAZY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getStopTime() {
        return stopTime;
    }

    public void setStopTime(Timestamp stopTime) {
        this.stopTime = stopTime;
    }

    public Long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType type) {
        this.taskType = type;
    }

    public Timestamp getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Timestamp lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public boolean isLastSuccess() {
        return isLastSuccess;
    }

    public void setLastSuccess(boolean lastSuccess) {
        isLastSuccess = lastSuccess;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }

    public ExecuteType getExecuteType() {
        return executeType;
    }

    public void setExecuteType(ExecuteType executeType) {
        this.executeType = executeType;
    }

    public Timestamp getRunTime() {
        return runTime;
    }

    public void setRunTime(Timestamp runTime) {
        this.runTime = runTime;
    }
}
