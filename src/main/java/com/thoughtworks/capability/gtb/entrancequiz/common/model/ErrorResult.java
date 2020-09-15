package com.thoughtworks.capability.gtb.entrancequiz.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResult {
    private Integer code;
    private Integer status;
    private String error;
    private String message;
    private Instant date;

    public ErrorResult(Integer status, String error, String message, Instant date) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.date = date;
    }
}
