package com.mokhovav.goodcare_scheduler;

import com.mokhovav.goodcare_scheduler.logger.Logger;
import com.mokhovav.goodcare_scheduler.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class GoodCareApplicationListener implements ApplicationListener {
    @Autowired
    private Logger logger;
    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            logger.debug("### Environment Init");
        } else if (event instanceof ApplicationPreparedEvent) {
            logger.debug("### Bean definition < " + event.getSource().getClass().getCanonicalName());
        } else if (event instanceof ApplicationReadyEvent) {
            logger.debug("### Application Ready");
            schedulerService.createTasksFromDBonStartUP();
        } else if (event instanceof ApplicationFailedEvent) {
            logger.error("### Exception: > " + ((ApplicationFailedEvent) event).getException());
        } else if (event instanceof ContextClosedEvent) {
//            ApplicationContext applicationContext = ((ContextClosedEvent) event).getApplicationContext();
//            grpcClient = applicationContext.getBean(GrpcClient.class);
//            grpcClient.close();
        }
    }
}
