package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dto.OrderDTO;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping("/seller/order")
@Api(tags = "卖家订单")
public class SellerOrderController {

    private final OrderService orderService;

    @Autowired
    public SellerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Integer")
    })
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<OrderDTO> orderDTOPage= orderService.findList(pageRequest);
        return ResultVOUtil.successPage(orderDTOPage);
    }

    @ApiOperation(value = "完结订单")
    @ApiImplicitParam(name = "order", value = "订单ID", required = true, dataType = "String")
    @PutMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestBody Map<String, String> order) {
        String orderId = order.get("orderId");
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【完结订单】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO result = orderService.finish(orderDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "order", value = "订单ID", required = true, dataType = "String")
    @PutMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestBody Map<String, String> order) {
        String orderId = order.get("orderId");
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【取消订单】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO result = orderService.cancel(orderDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "订单详情")
    @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "String")
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【订单详情】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return ResultVOUtil.success(orderDTO);
    }
}
