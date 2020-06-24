package com.yy.platform.component.starter.controller;


import com.yy.platform.component.starter.result.R;
import com.yy.platform.component.starter.result.ResultStatus;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token失效时，对返回消息进行处理
 */
@RestController
public class TokenErrorController extends BasicErrorController {

    public TokenErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        R rsponseInfo = R.Builder.failure().code(ResultStatus.UNAUTHORIZED).message((String) body.getOrDefault("message","")).build();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity(rsponseInfo, status);
    }
}