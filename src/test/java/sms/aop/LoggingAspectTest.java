package sms.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Configuration
public class LoggingAspectTest {

    @Mock
    private Logger mockLogger;
    @Mock
    private JoinPoint mockJoinPoint;
    @Mock
    private HttpServletRequest mockRequest;

    private LoggingAspect loggingAspect;

    @Before
    public void setUp() throws Exception {
        loggingAspect = new LoggingAspect(mockLogger, mockRequest);
    }

    @Test
    public void WhenInvokeRequestMappingAdviceDirectlyThenLogging() throws Exception {
        expectRequestAction("get", "/some-resource");
        expectJoinPointAction("foo.bar.SomeClass", "someMethod");
        String expectedMessage =
                "Request URL Mapping - [GET][/some-resource] ===> [foo.bar.SomeClass.someMethod()]";

        loggingAspect.loggingRequestMapping(mockJoinPoint);

        verify(mockLogger).info(expectedMessage);
    }

    private void expectRequestAction(String requestMethod, String requestURI) {
        when(mockRequest.getMethod()).thenReturn(requestMethod);
        when(mockRequest.getRequestURI()).thenReturn(requestURI);
    }

    private void expectJoinPointAction(String signatureTypeName, String signatureName) {
        final Signature mockSignature = Mockito.mock(Signature.class);
        when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getDeclaringTypeName()).thenReturn(signatureTypeName);
        when(mockSignature.getName()).thenReturn(signatureName);
    }
}