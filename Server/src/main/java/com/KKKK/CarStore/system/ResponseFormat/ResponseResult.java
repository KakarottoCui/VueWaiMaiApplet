package com.kbz1121.CarStore.system.ResponseFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {
    /*响应代码*/
    private int code;
    /*响应消息*/
    private String msg;
    /*响应数据*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseResult(ResponseCode code,T data){
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public ResponseResult(ResponseCode code,String msg){
        this.code = code.getCode();
        this.msg = msg;
    }

    public ResponseResult(ResponseCode code,String msg,T data){
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(ResponseCode code){
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = null;
    }

    public ResponseResult(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
