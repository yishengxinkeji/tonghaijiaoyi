package com.ruoyi.common.base;

import com.ruoyi.common.enums.ResponseEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseResult extends HashMap<String, Object> {
    /**
     * 初始化一个新创建的 Message 对象
     */
    public ResponseResult() {
    }

    public static ResponseResult success() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.put("code", ResponseEnum.SUCCESS.getCode());
        responseResult.put("msg", ResponseEnum.SUCCESS.getInfo());
        return responseResult;
    }

    public static ResponseResult error() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.put("code", ResponseEnum.FAIL.getCode());
        responseResult.put("msg", ResponseEnum.FAIL.getInfo());
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.put("code", ResponseEnum.FAIL.getCode());
        responseResult.put("msg", message);
        return responseResult;
    }


    public static ResponseResult responseResult(ResponseEnum responseEnum, Object data) {
        ResponseResult result = new ResponseResult();
        result.put("code", responseEnum.getCode());
        result.put("msg", responseEnum.getInfo());
        result.put("data", data);
        return result;
    }

    public static ResponseResult responseResult(ResponseEnum responseEnum) {
        ResponseResult result = new ResponseResult();
        result.put("code", responseEnum.getCode());
        result.put("msg", responseEnum.getInfo());
        return result;
    }

    public static ResponseResult responseResult(ResponseEnum responseEnum, Object data, Map<String,Object> map) {
        ResponseResult result = new ResponseResult();
        result.put("code", responseEnum.getCode());
        result.put("msg", responseEnum.getInfo());
        result.put("data", data);
        for (String key : map.keySet()){
            result.put(key,map.get(key));
        }
        return result;
    }

}
