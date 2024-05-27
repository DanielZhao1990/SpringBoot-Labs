package com.example.dlab.api;

import lombok.Data;

@Data
public class CorrectResponse {
    public int code;
    public int errorCount;
    public int warningCount;

}
