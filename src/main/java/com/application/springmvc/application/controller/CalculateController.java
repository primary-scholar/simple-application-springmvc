package com.application.springmvc.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.application.springmvc.api.param.NumberCalculateParam;
import com.application.springmvc.api.param.NumberOperationParam;
import com.application.springmvc.api.result.NumberCalculateResult;
import com.application.springmvc.api.result.NumberSeedResult;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class CalculateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);
    private static final Integer waitSeconds = 300;

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

    @RequestMapping(value = "/api/num/cal/mvc", method = RequestMethod.GET)
    public RpcResult calculateNumberInMvc(NumberOperationParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        NumberSeedResult first = operationHttpService.numberSeedGet(param);
        NumberSeedResult second = operationHttpService.numberSeedGet(param);
        NumberCalculateParam operationParam = NumberOperationBuilder.build(first, second, param.getOperation());
        NumberCalculateResult operationResult = operationHttpService.numberOperationPostInMvc(operationParam);
        return Objects.isNull(operationResult) ? RpcResultUtil.fail() : RpcResultUtil.success(operationResult);
    }

    @RequestMapping(value = "/api/num/cal/mvc/local", method = RequestMethod.POST)
    public RpcResult calculateNumberInMvcLocal(@RequestBody NumberCalculateParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        NumberCalculateResult build = NumberOperationBuilder.build(param);
        return  RpcResultUtil.success(build);
    }

    @RequestMapping(value = "/api/num/cal/mvc/thread", method = RequestMethod.GET)
    public RpcResult calculateNumberInMvcThread(NumberOperationParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        CountDownLatch stepF = new CountDownLatch(2);
        AtomicReference<NumberSeedResult> firstRef = new AtomicReference<>();
        AtomicReference<NumberSeedResult> secondRef = new AtomicReference<>();
        new Thread(() -> {
            firstRef.set(operationHttpService.numberSeedGet(param));
            stepF.countDown();
        }).start();
        new Thread(() -> {
            secondRef.set(operationHttpService.numberSeedGet(param));
            stepF.countDown();
        }).start();
        try {
            stepF.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        NumberSeedResult first =firstRef.get();
        NumberSeedResult second = secondRef.get();
        CountDownLatch stepS = new CountDownLatch(1);
        NumberCalculateParam operationParam = NumberOperationBuilder.build(first, second, param.getOperation());
        AtomicReference<NumberCalculateResult> thirdRef = new AtomicReference<>();
        new Thread(() -> {
            thirdRef.set(operationHttpService.numberOperationPostInMvc(operationParam));
            stepS.countDown();
        }).start();
        try {
            stepS.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        NumberCalculateResult operationResult = thirdRef.get();
        return Objects.isNull(operationResult) ? RpcResultUtil.fail() : RpcResultUtil.success(operationResult);
    }
}
