package com.mimu.application.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RpcResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
}
