package com.application.springmvc.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.application.springmvc.api.param.NumberCalculateParam;
import com.application.springmvc.api.result.NumberCalculateResult;
import com.application.springmvc.api.result.RpcResult;
import com.application.springmvc.api.result.RpcResultUtil;
import com.application.springmvc.application.service.NumberOperationHttpService;
import com.application.springmvc.application.utils.NumberOperationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CalculateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);

    @Autowired
    private NumberOperationHttpService operationHttpService;

    @RequestMapping(value = "/api/num/cal", method = RequestMethod.POST)
    public RpcResult calculateNumber(@RequestBody NumberCalculateParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        NumberCalculateParam operationParam = NumberOperationBuilder.build(param.getFirst(), param.getSecond(),
                param.getOperation());
        NumberCalculateResult operationResult = operationHttpService.numberOperationPost(operationParam);
        return Objects.isNull(operationResult) ? RpcResultUtil.fail() : RpcResultUtil.success(operationResult);
    }
}
