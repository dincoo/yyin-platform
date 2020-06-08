package com.yy.platform.component.starter.result;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel(value="返回基础信息",description="返回基础信息")
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

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


    public static class Builder<T> {

        private ResultStatus code;

        private T data;

        private String errorCode;

        private String message;

        public  Builder(){}

        public R.Builder code(ResultStatus code){
            this.code = code;
            return this;
        }

        public R.Builder data(T data){
            this.data = data;
            return this;
        }

        public R.Builder errorCode(String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public R.Builder message(String message){
            this.message = message;
            return this;
        }

        public static <T> R.Builder success(T data){
            R.Builder builder = new R.Builder<>();
            builder.code(ResultStatus.SUCCESS)
                    .message(ResultStatus.SUCCESS.getMessage())
                    .errorCode("0")
                    .data(data);
            return builder;
        }

        public static <T> R.Builder success(){
            R.Builder builder = new R.Builder<>();
            builder.code(ResultStatus.SUCCESS)
                    .message(ResultStatus.SUCCESS.getMessage())
                    .errorCode("0");
            return builder;
        }

        public static <T> R.Builder failure(){
            R.Builder builder = new R.Builder<>();
            builder.code(ResultStatus.INTERNAL_SERVER_ERROR)
                    .message(ResultStatus.INTERNAL_SERVER_ERROR.getMessage())
                    .errorCode("1");
            return builder;
        }

        public static <T> R.Builder badReq(){
            R.Builder builder = new R.Builder<>();
            builder.code(ResultStatus.BAD_REQUEST)
                    .message(ResultStatus.BAD_REQUEST.getMessage())
                    .errorCode("1");
            return builder;
        }

        public R build(){
            R r = new R();
            r.setCode(this.code.getCode());
            r.setMessage(this.message);
            r.setErrorCode(this.errorCode);
            r.setData(this.data);
            return r;
        }

    }

}
