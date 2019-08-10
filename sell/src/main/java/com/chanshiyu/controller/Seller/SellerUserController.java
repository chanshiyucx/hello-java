package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.VO.SellerVO;
import com.chanshiyu.constant.RedisConstatnt;
import com.chanshiyu.dataobject.SellerInfo;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.form.SellerLoginForm;
import com.chanshiyu.form.SellerRegisterForm;
import com.chanshiyu.service.SellerService;
import com.chanshiyu.utils.KeyUtil;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/seller/user")
@Slf4j
@Api(tags = "卖家管理")
public class SellerUserController {

    private SellerService sellerService;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public SellerUserController(SellerService sellerService, StringRedisTemplate stringRedisTemplate) {
        this.sellerService = sellerService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @ApiOperation(value = "注册用户")
    @ApiImplicitParam(name = "sellerRegisterForm", value = "用户信息", required = true, dataType = "SellerRegisterForm")
    @PostMapping("/register")
    public ResultVO<SellerVO> register(@Valid @RequestBody SellerRegisterForm sellerRegisterForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【注册用户】参数不正确，sellerRegisterForm={}", sellerRegisterForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        SellerInfo sellerInfo = sellerService.findSellerByUsername(sellerRegisterForm.getUsername());
        if (sellerInfo != null) {
            log.error("【注册用户】用户已存在，username={}", sellerRegisterForm.getUsername());
            throw new SellException(ResultEnum.USER_IS_EXIT);
        }

        // 暂不处理加密
        SellerInfo seller = new SellerInfo();
        BeanUtils.copyProperties(sellerRegisterForm, seller);
        seller.setSellerId(KeyUtil.genUniqueKey());
        SellerInfo result = sellerService.save(seller);

        SellerVO sellerVO = new SellerVO();
        BeanUtils.copyProperties(result, sellerVO);

        return ResultVOUtil.success(sellerVO);
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(name = "sellerLoginForm", value = "登录信息", required = true, dataType = "SellerLoginForm")
    @PostMapping("/login")
    public ResultVO<SellerVO> login(@Valid @RequestBody SellerLoginForm sellerLoginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            log.error("【卖家登录】参数不正确，sellerLoginForm={}", sellerLoginForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        SellerInfo sellerInfo = sellerService.findSellerByUsername(sellerLoginForm.getUsername());
        if (!sellerInfo.getPassword().equals(sellerLoginForm.getPassword())) {
            log.error("【卖家登录】密码错误，sellerLoginForm={}", sellerLoginForm);
            throw new SellException(ResultEnum.PASSWORD_ERROR);
        }

        // 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstatnt.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstatnt.TOKEN_PREFIX, token), sellerLoginForm.getUsername(), expire, TimeUnit.SECONDS);

        SellerVO sellerVO = new SellerVO();
        BeanUtils.copyProperties(sellerInfo, sellerVO);
        sellerVO.setToken(token);

        // 设置token到cookie
        //CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return ResultVOUtil.success(sellerVO);
    }
}
