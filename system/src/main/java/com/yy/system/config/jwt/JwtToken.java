package com.yy.system.config.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author YY
 * @date 2019/12/5
 * @description
 */
@Data
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = -2760518542076127676L;

    public JwtToken(String token) {
        super();
        this.token = token;
    }

    String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
