package com.jeferson.trajefino.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConsoleLogger {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerConsoleLogger.class);

    @EventListener(ApplicationReadyEvent.class)
    public void logSwaggerUrl() {
        logger.info("Swagger UI:");
        logger.info("http://localhost:8080/swagger-ui.html");
    }
}
