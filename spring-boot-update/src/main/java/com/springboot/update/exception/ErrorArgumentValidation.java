package com.springboot.update.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorArgumentValidation {

    private String field;
    private String fill;
    private String errorDetail;
}
