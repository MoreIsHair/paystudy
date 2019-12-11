package com.yy.system.config.shiro;

import cn.hutool.core.util.ObjectUtil;
import com.yy.common.constant.enums.ResultEnum;
import com.yy.system.config.jwt.JwtToken;
import com.yy.system.config.jwt.JwtUtil;
import com.yy.system.model.entity.SystemUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2019/12/5
 * @description 自定顶shiro域
 */

@Component
public class PayRealm extends AuthorizingRealm {

    /**
     * shiro 会用来判断token是否合法，所以的重写
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权判断
     * 需要从数据库中查询所有权限、角色 （对应注解上的名名称）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException(ResultEnum.SHIRO_AUTHENTICATION_INVALID.getCode().toString());
        }

        //TODO 需要从数据库中查找用户
        SystemUser user = new SystemUser();
        user.setUsername("123");
        user.setPassword("123");
        if (ObjectUtil.isNull(user)) {
            throw new AuthenticationException(ResultEnum.SHIRO_AUTHENTICATION_WRONG.getCode().toString());
        }

        if (!JwtUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException(ResultEnum.SHIRO_AUTHENTICATION_WRONG.getCode().toString());
        }

        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
