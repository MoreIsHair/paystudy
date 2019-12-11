package com.yy.system.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YY
 * @date 2019/12/5
 * @description
 */
@Api(tags = "用户API,登录已在过滤器中实现（/user/login）")
@RestController
@RequestMapping("/user")
public class UserApi {

}
