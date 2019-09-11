package com.chanshiyu.controller;

import com.chanshiyu.enums.ApiStatusEnums;
import com.chanshiyu.util.CommJSONResult;
import com.chanshiyu.util.FastDFSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author shiyu
 * @date 2019/9/10 10:52
 * @Description: 工具Controller
 */
@RestController
@RequestMapping("/tool")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ToolController {

    private final FastDFSClient fastDFSClient;

    private final String AVATARHOST = "http://192.168.205.10:8888/group1/";

    @PostMapping("/upload")
    public CommJSONResult<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return CommJSONResult.errorMsg(ApiStatusEnums.FILE_NOT_EMPTY.getMsg());
        }
        String url = fastDFSClient.uploadBase64(file);
        String avatar = AVATARHOST + url;
        return CommJSONResult.ok(avatar);
    }

}
