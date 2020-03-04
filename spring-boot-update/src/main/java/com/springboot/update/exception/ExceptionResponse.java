package com.springboot.update.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private Integer status;
    private String path;
}
