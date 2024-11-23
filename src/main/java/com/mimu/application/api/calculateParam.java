package com.mimu.application.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class calculateParam implements Serializable {
    /**
     * 两个数 算术第一个
     */
    private Integer first;
    /**
     * 两个数 算术第二个
     */
    private Integer second;
    /**
     * 描述
     */
    private String description;
}
