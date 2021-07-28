package com.cliente.escola.gradecurricular.handler;

import com.cliente.escola.gradecurricular.exceptions.MateriaException;
import com.cliente.escola.gradecurricular.models.ErrorMapResponse;
import com.cliente.escola.gradecurricular.models.ErrorMapResponse.ErrorMapResponseBuilder;
import com.cliente.escola.gradecurricular.models.ErrorResponse;
import com.cliente.escola.gradecurricular.models.ErrorResponse.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> erros = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(erro -> {
            String campo = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
        errorMap.erros(erros)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
    }

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaExeption(MateriaException materiaException) {
        ErrorResponseBuilder errorResponseBuilder = ErrorResponse.builder();
        errorResponseBuilder.httpStatus(materiaException.getHttpStatus().value());
        errorResponseBuilder.mensagem(materiaException.getMessage());
        errorResponseBuilder.timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(materiaException.getHttpStatus()).body(errorResponseBuilder.build());
    }
}
