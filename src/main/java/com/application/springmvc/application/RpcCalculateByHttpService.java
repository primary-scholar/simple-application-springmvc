package com.application.springmvc.application;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.application.springmvc.api.CalculateResult;
import com.application.springmvc.api.RpcResult;
import com.application.springmvc.api.calculateParam;
import com.mimu.common.log.http.CommonHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcCalculateByHttpService {
    private static final String RPC_GET_URL = "http://localhost:8082/api/local/num/add?first=%s&second=%s&description=%s";
    private static final String RPC_POST_URL = "http://localhost:8082/api/local/num/multi";

    @Autowired
    private CommonHttpClient commonHttpClient;

    public CalculateResult rpcGetResult(calculateParam param) {
        String rpcResult = commonHttpClient.get(String.format(RPC_GET_URL, param.getFirst(), param.getSecond(), param.getDescription()));
        RpcResult<CalculateResult> parsedObject = JSONObject.parseObject(rpcResult,new TypeReference<RpcResult<CalculateResult>>(){});
        CalculateResult data = parsedObject.getData();
        return data;
    }

    public CalculateResult rpcPostResult(calculateParam param) {
        String rpcResult = commonHttpClient.post(RPC_POST_URL,JSONObject.toJSONString(param));
        RpcResult<CalculateResult> parsedObject = JSONObject.parseObject(rpcResult,new TypeReference<RpcResult<CalculateResult>>(){});
        CalculateResult data = parsedObject.getData();
        return data;
    }
}
