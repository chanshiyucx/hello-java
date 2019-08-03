package com.chanshiyu.controller.Buyer;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.convert.OrderForm2OrderDTOConvert;
import com.chanshiyu.dto.OrderDTO;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.form.OrderForm;
import com.chanshiyu.service.BuyerService;
import com.chanshiyu.service.OrderService;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
@Api(tags = "买家订单")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @ApiOperation(value="创建订单")
    @PostMapping("/create")
    public ResultVO<OrderDTO> create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConvert.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        return ResultVOUtil.success(createResult);
    }

    @ApiOperation(value="订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家 openid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Integer"),
    })
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if(StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    @ApiOperation(value="订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家 openid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单 ID", required = true, dataType = "String")
    })
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    @ApiOperation(value="取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家 openid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单 ID", required = true, dataType = "String")
    })
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
         buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
