package sms.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutDefinition {

   @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
           " || @annotation(org.springframework.web.bind.annotation.GetMapping)" +
           " || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void handlerMethod() {}

   @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler) " +
           "&& args(ex)")
    public void exceptionHandler(Exception ex) {}
}