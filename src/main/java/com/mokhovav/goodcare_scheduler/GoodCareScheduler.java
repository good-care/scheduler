package com.mokhovav.goodcare_scheduler;

import com.mokhovav.goodcare_scheduler.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.mokhovav.goodcare_scheduler"})
public class GoodCareScheduler {
    @Autowired
    private Logger logger;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .profiles("dev")
                .logStartupInfo(false)
                .sources(GoodCareScheduler.class)
                .run(args);
    }

    /** Only for learning*/
    @Bean
    CommandLineRunner commandLineRunner(){
        logger.debug("### ApplicationRunner");
        return args -> {
            for (String arg:args) {
                logger.info(arg);
            }
        };
    }

    /** Only for learning*/
    @Bean
    ApplicationRunner applicationRunner(){
        logger.debug("### CommandLineRunner");
        return args -> {
            args.getNonOptionArgs().forEach(s -> logger.info(s));
        };
    }
}
