package com.dmj.controller.admin;

import com.dmj.constant.MessageConstant;
import com.dmj.context.BaseContext;
import com.dmj.manager.CosManager;
import com.dmj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Resource
    private CosManager cosManager;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestPart("file")MultipartFile multipartFile){
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/%s/%s/%s", "take-out", BaseContext.getCurrentId(), filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            filepath = String.format("https://%s.cos.%s.myqcloud.com%s",cosManager.cosClientConfig.getBucket(),cosManager.cosClientConfig.getRegion(),filepath);
            // 返回可访问地址
            return Result.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }
}
