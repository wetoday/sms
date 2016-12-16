package sms.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect // not @Component => not component-scan
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void loggingRequestMapping(JoinPoint joinPoint) {
        String mappingMethod = parseMethodName(joinPoint.toString());
        String requestMappingInfo = String.format("Request URL Mapping - [%S][%s] ===> [%s]",
                request.getMethod(), request.getRequestURI(), mappingMethod);

        logger.info(requestMappingInfo);
    }

    // ex. "execution(String foo.bar.method(..))" ==> "foo.bar.method()"
    private String parseMethodName(String joinPoint) {
        Pattern pattern = Pattern.compile("^.+ ([\\w.]+)\\(.*");
        Matcher matcher = pattern.matcher(joinPoint);

        return matcher.find() ? matcher.group(1) + "()" : "Method Name Parsing Error";
    }
}