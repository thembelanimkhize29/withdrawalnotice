package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvestorNotFoundException extends RuntimeException {
    public InvestorNotFoundException(String message) {
        super(message);
    }
}
