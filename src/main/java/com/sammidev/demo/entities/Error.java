package com.sammidev.demo.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class Error implements Serializable {

    private String code;
    private String message;

    @JsonCreator
    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
