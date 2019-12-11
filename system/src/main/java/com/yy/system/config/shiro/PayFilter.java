package com.yy.system.config.shiro;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yy.common.constant.enums.ResultEnum;
import com.yy.common.util.Result;
import com.yy.system.config.jwt.JwtToken;
import com.yy.system.config.jwt.JwtUtil;
import com.yy.system.model.entity.SystemUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author YY
 * @date 2019/12/5
 * @description 自定义shiro过滤器，用来做权限拦截处理
 */

@Component
public class PayFilter extends BasicHttpAuthenticationFilter {

    /**
     * 请求标识
     */
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 请求标识(存在cookie)
     */
    protected static final String AUTHORIZATION_COOKIE = "_Authorization_Cookie_";
    //protected static final String SECRET = "_Authorization_Secret_";
    /**
     * 登录请求的api
     */
    protected static final String LOGIN_URL_CONTAIN = "login";

    /**
     * 利用preHandle中先调用isAccessAllowed 处理option请求
     * isAccessAllowed 和onAccessDenied 有一个为true则会放行
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        String servletPath = httpServletRequest.getServletPath();
        if (StrUtil.isNotBlank(servletPath) &&
                servletPath.contains(LOGIN_URL_CONTAIN)) {
            try {
                // 处理登录请求
                executeLogin(request, response);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                onLoginFailure(null, new AuthenticationException(e.getMessage()), request, response);
                return false;
            }
        }
        return false;
    }

    /**
     * 验证token
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        String servletPath = httpServletRequest.getServletPath();
        if (StrUtil.isNotBlank(servletPath) &&
                servletPath.contains(LOGIN_URL_CONTAIN)) {
            // 很重要，阻止shiro继续往下调用，因为response不关登录成功与否都被使用完了
            return false;
        }
        String authzHeader = getAuthzHeader(request);
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            if (StrUtil.isBlank(authzHeader)) {
                httpServletResponse.setStatus(HttpStatus.OK.value());
                writer.write(JSONUtil.toJsonStr(Result.fail(ResultEnum.SHIRO_AUTHENTICATION_INVALID)));
                return false;
            }
            String username = JwtUtil.getUsername(authzHeader);
            if (StrUtil.isBlank(username)) {
                writer.write(JSONUtil.toJsonStr(Result.fail(ResultEnum.SHIRO_AUTHENTICATION_WRONG)));
                return false;
            }
            //TODO 需要从数据库中查找用户
            SystemUser user = new SystemUser();
            if (ObjectUtil.isNull(user)) {
                writer.write(JSONUtil.toJsonStr(Result.fail(ResultEnum.SHIRO_AUTHENTICATION_WRONG)));
                return false;
            }
        }
        return true;
    }


    /**
     * 修改获取标识的方式（head，parameter，cookie）
     */
    @Override
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String header = httpRequest.getHeader(AUTHORIZATION_HEADER);
        Cookie cookie = null;
        Cookie[] cookies = httpRequest.getCookies();
        if (ObjectUtil.isNotNull(cookies)) {
            for (Cookie c : cookies) {
                if (AUTHORIZATION_COOKIE.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }
        return StrUtil.isNotBlank(header)
                ? header : StrUtil.isNotBlank(httpRequest.getParameter(AUTHORIZATION_HEADER))
                ? httpRequest.getParameter(AUTHORIZATION_HEADER)
                : ObjectUtil.isNotNull(cookie) ? cookie.getValue() : header;

    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        Assert.notBlank(username, "登录名称不能为空");
        Assert.notBlank(password, "登录密码不能为空");
        String sign = JwtUtil.sign(username, password);
        JwtToken jwtToken = new JwtToken(sign);
        // 提交给realm进行登入，如果错误会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return onLoginSuccess(jwtToken, getSubject(request, response), request, response);
    }

    /**
     * 登录成功
     * （写回客户端信息）
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(Result.success(MapUtil.of(AUTHORIZATION_HEADER, token.getCredentials()))));
        writer.flush();
        writer.close();
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            Optional<ResultEnum> first = Arrays.stream(ResultEnum.values()).filter(c -> c.getCode().toString().equals(e.getMessage())).findFirst();
            if (first.isPresent()) {
                writer.write(JSONUtil.toJsonStr(Result.fail(first.get())));
                writer.flush();
                return false;
            }
            writer.write(JSONUtil.toJsonStr(Result.fail(e.getMessage())));
            writer.flush();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
