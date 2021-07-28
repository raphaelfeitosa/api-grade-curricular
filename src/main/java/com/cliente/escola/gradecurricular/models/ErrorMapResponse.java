package com.cliente.escola.gradecurricular.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class ErrorMapResponse {

    private int httpStatus;
    private Map<String, String> erros;
    private Long timeStamp;
}
