package com.cliente.escola.gradecurricular.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private String mensagem;
    private int httpStatus;
    private long timeStamp;
}
