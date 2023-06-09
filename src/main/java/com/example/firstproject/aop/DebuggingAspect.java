package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Slf4j
public class DebuggingAspect {
    @Pointcut("execution(* com.example.firstproject.service.CommentService.*(..))")
    private void cut() {}


    @Before("cut()")  // 실행 시점 설정 : cut()의 대상이 수행되기 이전
    public void loggingArgs(JoinPoint joinPoint) {  // cut의 대상 메소드
        // 입력값 가져오기
        Object[] args = joinPoint.getArgs();
        // 클래스명
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        // 입력값 로깅하기
        for (Object obj : args) {
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }
    @AfterReturning(value = "cut()", returning = "returnObj") // 실행 시점 설정 : cut()의 대상이 수행되기 이후
    public void loggingReturnValue(JoinPoint joinPoint,
                                   Object returnObj) {      // 리턴값
        // 클래스명
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        // 반환값 로깅
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);
    }
}
