package com.cliente.escola.gradecurricular.v1.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CursoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;

    public CursoException(final String mensagem, final HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }
}
