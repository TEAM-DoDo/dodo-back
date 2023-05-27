package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleUserNotFoundException(UserNotFoundException ex)
    {
        //출력
        System.out.println("UserNotFoundException 발생 : " + ex.getMessage());

        //반환
        ErrorResponse errorResponse = ErrorResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //404코드
    }

    @ExceptionHandler(DoNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleDoNotFoundException(DoNotFoundException ex)
    {
        //출력
        System.out.println("DoNotFoundException 발생 : " + ex.getMessage());

        //반환
        ErrorResponse errorResponse = ErrorResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleScheduleNotFoundException(ScheduleNotFoundException ex)
    {
        //출력
        System.out.println("ScheduleNotFoundException 발생 : " + ex.getMessage());

        //반환
        ErrorResponse errorResponse = ErrorResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleFileNotFoundException(FileNotFoundException ex)
    {
        //출력
        System.out.println("FileNotFoundException 발생 : " + ex.getMessage());

        //반환
        ErrorResponse errorResponse = ErrorResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
