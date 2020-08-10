package com.sammidev.demo.exception;

import com.sammidev.demo.entities.Error;
import com.sammidev.demo.entities.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class PenghinapanExceptionHandler implements WebExceptionHandler {

    private ObjectMapper objectMapper;

    public PenghinapanExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        if (throwable instanceof PenghinapanNotFoundException) {

            Errors errors = new Errors();
            Error error = new Error("404", throwable.getMessage());
            errors.setError(error);
            try {
                serverWebExchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));
                return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return Mono.empty();
            }
        }
        return Mono.error(throwable);
    }
}
