package com.compass.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PageNotFoundException.class)
    @ResponseBody
    public Mono<ExceptionResponseBody> handlePageNotFoundException(PageNotFoundException exc) {
        ExceptionResponseBody error = new ExceptionResponseBody();

        error.setTimeStamp(Instant.now());
        error.setStatus(HttpResponseStatus.NOT_FOUND.code());
        error.addError(exc.getMessage());
        LOGGER.error(exc.getMessage(), exc);

        return Mono.just(error);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    public Mono<ExceptionResponseBody> handleInternalServerErrorException(Exception exc) {
        ExceptionResponseBody error = new ExceptionResponseBody();

        error.setTimeStamp(Instant.now());
        error.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        error.addError(exc.getMessage());
        LOGGER.error(exc.getMessage(), exc);

        return Mono.just(error);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public Mono<ExceptionResponseBody> handleBadRequestException(Exception exc) {
        ExceptionResponseBody error = new ExceptionResponseBody();

        error.setTimeStamp(Instant.now());
        error.setStatus(HttpResponseStatus.BAD_REQUEST.code());
        error.addError(exc.getMessage());
        LOGGER.error(exc.getMessage(), exc);

        return Mono.just(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Mono<ExceptionResponseBody> handleGenericException(Exception exc) {
        ExceptionResponseBody error = new ExceptionResponseBody();

        error.setTimeStamp(Instant.now());
        error.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        error.addError(exc.getMessage());
        LOGGER.error(exc.getMessage(), exc);

        return Mono.just(error);
    }


}