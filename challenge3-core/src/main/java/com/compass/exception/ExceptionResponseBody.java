package com.compass.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseBody {

    private int status;

    private String path;

    private Instant timeStamp;

    private List<String> errors = new ArrayList<>();

    public void addError(String message){
        this.errors.add(message);
    }

}
