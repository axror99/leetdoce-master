package com.example.leetdoce.exception;

import java.io.IOException;

public class GetBytesException extends RuntimeException{
    public GetBytesException(IOException message) {
        super(message);
    }
}
