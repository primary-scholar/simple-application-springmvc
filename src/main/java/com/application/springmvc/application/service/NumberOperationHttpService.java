package com.application.springmvc.application.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.application.springmvc.api.param.NumberCalculateParam;
import com.application.springmvc.api.result.NumberCalculateResult;
import com.application.springmvc.api.param.NumberOperationParam;
import com.application.springmvc.api.result.RpcResult;
import com.application.springmvc.api.result.NumberSeedResult;
import com.mimu.common.log.http.CommonHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberOperationHttpService {
    private static final String RPC_GET_URL = "http://localhost:8082/api/num/gen?number=%s&p1=%s";
    private static final String RPC_POST_URL = "http://localhost:8082/api/num/cal";

    @Autowired
    private CommonHttpClient commonHttpClient;

    public NumberSeedResult numberSeedGet(NumberOperationParam param) {
        try {
            String rpcResult = commonHttpClient.get(String.format(RPC_GET_URL, param.getNumber(), param.getP1()));
            RpcResult<NumberSeedResult> parsedObject = JSONObject.parseObject(rpcResult,
                    new TypeReference<RpcResult<NumberSeedResult>>() {
                    });
            return parsedObject.getData();
        } catch (Exception e) {
            return null;
        }
    }

    public NumberCalculateResult numberOperationPost(NumberCalculateParam param) {
        try {
            String rpcResult = commonHttpClient.post(RPC_POST_URL, JSONObject.toJSONString(param));
            RpcResult<NumberCalculateResult> parsedObject = JSONObject.parseObject(rpcResult,
                    new TypeReference<RpcResult<NumberCalculateResult>>() {
                    });
            return parsedObject.getData();
        } catch (Exception e) {
            return null;
        }
    }
}
