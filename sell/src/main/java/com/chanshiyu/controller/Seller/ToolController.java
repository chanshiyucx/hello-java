package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传等工具
 */
@RestController
@RequestMapping("/tool")
@Slf4j
@Api(tags = "工具接口")
public class ToolController {

    @ApiOperation(value = "文件上传")
    @ApiImplicitParam(name = "file", value = "上传文件", required = true, dataType = "file")
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("【文件上传】上传文件不能为空");
            throw new SellException(ResultEnum.FILE_NOT_EMPTY);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为: " + fileName);


        MultiValueMap<String, MultipartFile> parts = new LinkedMultiValueMap<>();
        parts.add("smfile", file);

        String basicAuth = "Basic 14ac5499cfdd2bb2859e4476d2e5b1d2bad079bf";
        log.info("basicAuth: " + basicAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(basicAuth);

        return ResultVOUtil.success();
    }
}
