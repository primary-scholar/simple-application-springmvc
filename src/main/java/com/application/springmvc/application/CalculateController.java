package com.application.springmvc.application;


import com.alibaba.fastjson.JSONObject;
import com.application.springmvc.api.CalculateResult;
import com.application.springmvc.api.RpcResult;
import com.application.springmvc.api.RpcResultUtil;
import com.application.springmvc.api.calculateParam;
import com.mimu.common.log.http.CommonHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);

    @Autowired
    private RpcCalculateByHttpService rpcCalculateByHttpService;

    @RequestMapping(value = "/api/local/num/add", method = RequestMethod.GET)
    public RpcResult getLocalMethod(calculateParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        CalculateResult calculateResult = new CalculateResult(param.getFirst() + param.getSecond(),
                param.getDescription());
        return RpcResultUtil.success(calculateResult);
    }


    @RequestMapping(value = "/api/local/num/multi", method = RequestMethod.POST)
    public String postLocalMethod(@RequestBody calculateParam param) {
        LOGGER.info("post param:{}", JSONObject.toJSONString(param));
        CalculateResult calculateResult = new CalculateResult(param.getFirst() * param.getSecond(),
                param.getDescription());
        RpcResult<CalculateResult> success = RpcResultUtil.success(calculateResult);
        return JSONObject.toJSONString(success);
    }

    @RequestMapping(value = "/api/remote/num/add", method = RequestMethod.GET)
    public RpcResult getRemoteMethod(calculateParam param) {
        LOGGER.info("get param:{}", JSONObject.toJSONString(param));
        CalculateResult calculateResult = rpcCalculateByHttpService.rpcGetResult(param);
        return RpcResultUtil.success(calculateResult);
    }


    @RequestMapping(value = "/api/remote/num/multi", method = RequestMethod.POST)
    public String postRemoteMethod(@RequestBody calculateParam param) {
        LOGGER.info("post param:{}", JSONObject.toJSONString(param));
        CalculateResult calculateResult = rpcCalculateByHttpService.rpcPostResult(param);
        RpcResult<CalculateResult> success = RpcResultUtil.success(calculateResult);
        return JSONObject.toJSONString(success);
    }

}
