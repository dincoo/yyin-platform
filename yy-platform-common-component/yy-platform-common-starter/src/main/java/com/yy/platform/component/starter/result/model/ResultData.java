package com.yy.platform.component.starter.result.model;

import com.yy.platform.component.starter.result.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 092506540
 * @Date: 2019/10/22 14:45
 * @Description: 统一返回结果实体类
 */
@Data
@ApiModel(value="返回基础信息",description="返回基础信息")
public class ResultData<T> implements Serializable {

    /**
     * 返回结果代码
     */
    @ApiModelProperty(value = "返回结果代码", example = "200")
    private String code;

    /**
     * 返回结果数据
     */
    @ApiModelProperty(value = "返回结果数据")
    private T data;

    /**
     * 返回结果错误编码
     */
    @ApiModelProperty(value = "返回结果错误编码")
    private String errorCode;

    /**
     * 返回结果描述
     */
    @ApiModelProperty(value = "返回结果描述")
    private String message;

    private ResultData(ResultStatus resultStatus, T data){
        this.code = resultStatus.getCode();
        this.data = data;
        this.message = resultStatus.getMessage();
    }

    private ResultData(String code, T data, String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    private ResultData(String code, T data, String errorCode, String message){
        this.code = code;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ResultData<Void> success(){
        return new ResultData<Void>(ResultStatus.SUCCESS.getCode(), null, ResultStatus.SUCCESS.getMessage());
    }

    public static <T> ResultData<T> success(T data){
        return new ResultData<T>(ResultStatus.SUCCESS, data);
    }

    public static <T> ResultData<T> success(String code, T data, String message){
        return new ResultData<T>(code, data, message);
    }

    public static <T> ResultData<T> success(ResultStatus resultStatus, T data){
        if(null == resultStatus){
            return success(data);
        }
        return new ResultData<T>(resultStatus, data);
    }

    public static ResultData<Void> failure(){
        return failure(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResultData<T> failure(ResultStatus resultStatus){
        return new ResultData<T>(resultStatus, null);
    }

    public static <T> ResultData<T> failure(String message){
        return failure(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), null, message);
    }

    public static <T> ResultData<T> failure(ResultStatus resultStatus, String message){
        return failure(resultStatus.getCode(), null, message);
    }

    public static <T> ResultData<T> failure(T data){
        return failure(ResultStatus.INTERNAL_SERVER_ERROR, data);
    }

    public static <T> ResultData<T> failure(String code, T data, String message){
        return new ResultData<T>(code, data, message);
    }

    public static <T> ResultData<T> failure(String code, T data, String errorCode, String message){
        return new ResultData<T>(code, data, errorCode, message);
    }

    public static <T> ResultData<T> failure(String code, String errorCode, String message){
        return new ResultData<T>(code, null, errorCode, message);
    }

    public static <T> ResultData<T> failure(ResultStatus resultStatus, T data){
        if(null == resultStatus){
            return failure(data);
        }
        return new ResultData<T>(resultStatus, data);
    }

}
