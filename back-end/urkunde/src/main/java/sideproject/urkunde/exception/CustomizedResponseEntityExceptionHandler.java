package sideproject.urkunde.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * 모든 컨트롤러가 실행될때 반드시 해당 어노테이션을 갖고 있는 bean이 자동으로 실행 .
 * 에러가 발생한다고 하면 핸들러에서 등록시켰던 에러 메소드가 실행이될 수 있다.
 */
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler  {



    /**
     * controller에서 IllegalAugumentException 발생하면 이 method가 실행된다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleUserNotFountException(Exception ex, WebRequest request){

        // 우리가 만들었던 error 형식, 일반 error 문에서는 백엔드 코드가 노출될 위험이 있기 때문에 필요한 에러 데이터만 반환하고자 한다.
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND); // 404번 error
    }

    /**
     * 사용자가 잘못된 값을 넣어서 valid 문제가 생겼을때 처리 하는 메서드
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex){

        ExceptionResponse exceptionResponse =
               new ExceptionResponse(new Date(), "Validation Failed",
                                    ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());


        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST); // 400번 error
    }






}
