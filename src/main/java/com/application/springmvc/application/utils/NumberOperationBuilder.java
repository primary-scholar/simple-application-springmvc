package com.application.springmvc.application.utils;


import com.application.springmvc.api.operation.NumberOperation;
import com.application.springmvc.api.operation.NumberOperationSelector;
import com.application.springmvc.api.param.NumberCalculateParam;
import com.application.springmvc.api.param.NumberOperationParam;
import com.application.springmvc.api.result.NumberCalculateResult;
import com.application.springmvc.api.result.NumberSeedResult;

public class NumberOperationBuilder {

    public static NumberSeedResult build(NumberOperationParam param) {
        NumberSeedResult seedResult = new NumberSeedResult();
        seedResult.setNumber(param.getNumber());
        return seedResult;
    }

    public static NumberCalculateResult build(NumberCalculateParam param) {
        Integer operation = param.getOperation();
        NumberOperation select = NumberOperationSelector.select(operation);
        NumberSeedResult first = param.getFirst();
        NumberSeedResult second = param.getSecond();
        Integer operate = select.operate(first, second);
        NumberCalculateResult result = new NumberCalculateResult();
        result.setOperation(operation);
        result.setFirst(first);
        result.setSecond(second);
        result.setResult(operate);
        return result;
    }

    public static NumberCalculateParam build(NumberSeedResult first, NumberSeedResult second, Integer operation) {
        NumberCalculateParam param = new NumberCalculateParam();
        param.setFirst(first);
        param.setSecond(second);
        param.setOperation(operation);
        return param;
    }

}
