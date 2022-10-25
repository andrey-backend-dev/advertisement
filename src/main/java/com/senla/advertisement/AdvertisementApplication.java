package com.senla.advertisement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class AdvertisementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertisementApplication.class, args);
	}

	@Bean
    @Scope("prototype")
    public Logger logger(final InjectionPoint ip) {
        final Class<?> lClass;
        if (null != ip.getMethodParameter()) {
            lClass = ip.getMethodParameter().getContainingClass();
        } else {
            lClass = ip.getField().getDeclaringClass();
        }
        return LogManager.getLogger(lClass);
    }
}
