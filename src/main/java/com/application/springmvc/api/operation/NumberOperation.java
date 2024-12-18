package com.application.springmvc.api.operation;

import com.application.springmvc.api.result.NumberSeedResult;

public interface NumberOperation {

    Integer getOperation();

    String getOperationDesc();

    Integer operate(NumberSeedResult first, NumberSeedResult second);

}
