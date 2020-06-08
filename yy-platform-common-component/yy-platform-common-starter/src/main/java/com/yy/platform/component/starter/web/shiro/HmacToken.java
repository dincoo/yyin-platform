package com.yy.platform.component.starter.web.shiro;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

@Getter
@Setter
public class HmacToken implements AuthenticationToken {
    private static final long serialVersionUID = -7838912794581842158L;

    private String clientKey;// 客户标识（可以是用户名、app id等等）
    private String digest;// 消息摘要
    private String timeStamp;// 时间戳
    private Map<String, String[]> parameters;// 访问参数
    private String host;// 客户端IP

    public HmacToken(String clientKey,String timeStamp,String digest
            ,String host,Map<String, String[]> parameters){
        this.clientKey = clientKey;
        this.timeStamp = timeStamp;
        this.digest = digest;
        this.host = host;
        this.parameters = parameters;
    }

    @Override
    public Object getPrincipal() {
        return this.clientKey;
    }
    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }

}
