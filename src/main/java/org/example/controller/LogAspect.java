package org.example.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    @Around("execution(public * org.example.service.LoadService.*(..))")
    public Object around(ProceedingJoinPoint p) {
        Object res;
        System.out.println("Method: " + p.getSignature().getName());
        System.out.println("Args: " + Arrays.toString(p.getArgs()));
        long a = System.currentTimeMillis();// not business logic
        try {
            res = p.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.out.println("Method execution spends " + (System.currentTimeMillis() - a));// not business logic

        return res;

    }
}
