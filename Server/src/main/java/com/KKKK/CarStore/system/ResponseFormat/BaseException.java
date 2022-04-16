package com.kbz1121.CarStore.system.ResponseFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类，继承运行时异常，确保事务正常回滚
 *
 * @author NULL
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{

    private ResponseCode code;

    public BaseException(ResponseCode code) {
        this.code = code;
    }

    public BaseException(Throwable cause, ResponseCode code) {
        super(cause);
        this.code = code;
    }
}
