package com.yy.platform.component.starter.configure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Auther: 092506540
 * @Date: 2019/12/16 17:03
 * @Description: 使用Jackson进行数据序列化时设置Long的精度丢失问题
 */
@JsonComponent
//@ConditionalOnExpression("${me.mall.serialize.jsckson.enabled:false}")
//yy.platform.serialize.fastjson.enabled 值为false或者未设置时，默认使用Jackson进行结果的序列化
@ConditionalOnProperty(value = "yy.platform.serialize.fastjson.enabled", havingValue = "false", matchIfMissing = true)
public class JacksonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        /**
         * 可配置项：
         * 1.Include.Include.ALWAYS : 默认
         * 2.Include.NON_DEFAULT : 默认值不序列化
         * 3.Include.NON_EMPTY : 属性为 空（""） 或者为 NULL 都不序列化
         * 4.Include.NON_NULL : 属性为NULL 不序列化
         **/
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        objectMapper.setDateFormat("yyyy-MM-dd HH:mm:ss");


        SimpleModule module = new SimpleModule();
        /**
         * 序列化时,将所有的long变成string
         */
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        /**
         * 序列化时,将所有的时间类型进行格式化
         */
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
