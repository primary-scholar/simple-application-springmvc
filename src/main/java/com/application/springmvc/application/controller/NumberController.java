package com.application.springmvc.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.application.springmvc.api.param.NumberOperationParam;
import com.application.springmvc.api.result.NumberSeedResult;
import com.application.springmvc.api.result.RpcResult;
import com.application.springmvc.api.result.RpcResultUtil;
import com.application.springmvc.application.utils.NumberOperationBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberController.class);

    @RequestMapping(value = "/api/num/gen", method = RequestMethod.GET)
    public RpcResult genNumberWithGet(NumberOperationParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        param.setNumber(param.getNumber() + NumberUtils.INTEGER_ONE);
        NumberSeedResult build = NumberOperationBuilder.build(param);
        return RpcResultUtil.success(build);
    }
}
