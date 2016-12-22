package sms.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutDefinition {

   @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {}

   @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler) " +
           "&& args(ex)")
    public void globalExceptionHandler(Exception ex) {}
}
