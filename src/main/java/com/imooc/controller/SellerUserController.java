package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.SellerService;
import com.imooc.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hellozjf
 * @date 2018/4/8 19:54
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private WxMpService wxOpenService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid", defaultValue = "") String openid,
                              @RequestParam(value = "code", defaultValue = "") String code,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        log.debug("openid={} code={}", openid, code);
        if (openid.equals("") && !code.equals("")) {
            // 说明是从廖师兄那里跳转过来的，因此要先设置正确的openid
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
            try {
                wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
            } catch (WxErrorException e) {
                log.error("【微信网页授权】{}", e);
                throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
            }
            openid = wxMpOAuth2AccessToken.getOpenId();
            log.debug("correct openid={} code={}", openid, code);
        }

        // 1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/seller/order/list");
            return new ModelAndView("common/error");
        }

        // 2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        // set方法里面，第一个是redis的key，第二个是redis的value，第三个是redis的过期时间，第四个是redis过期时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        // 跳转建议用完整路径方式跳转，不要用相对路径方式跳转
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {
        // 1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
