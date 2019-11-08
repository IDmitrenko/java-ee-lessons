package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogPlaces {

    private static final Logger logger = LoggerFactory.getLogger(LogPlaces.class);

    @AroundInvoke
    public Object printLog(InvocationContext ctx) throws Exception {
        logger.info("Вызван метод " + ctx.getMethod().getName());
        return ctx.proceed();
    }
}
