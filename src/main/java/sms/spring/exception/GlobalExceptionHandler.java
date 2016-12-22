package sms.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody Exception handleException(Exception ex) {
        return ex;
    }

/*    // 케이스 1) Exception 예외 발생 시 예외 뷰로 전환
     @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return "error";
    }*/

/*    // 케이스 2) 특정 예외가 발생하면 지정된 HTTP 응답코드를 발생시키는 방법
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "내부 서버 에러 발생")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {}*/
}