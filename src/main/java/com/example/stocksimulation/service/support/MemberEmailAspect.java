package com.example.stocksimulation.service.support;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class MemberEmailAspect {
    @Before("execution(* com.example.stocksimulation.web.controller.*.*(..))")
    public void beforeControllerMethodExecution() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String memberEmail = request.getUserPrincipal().getName();
        request.setAttribute("memberId", memberEmail);
    }
}
