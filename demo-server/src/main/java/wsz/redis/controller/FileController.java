package wsz.redis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wsz.redis.common.CommonResult;
import wsz.redis.service.FileService;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 文件工具类
 * @author: WSZ
 * @create: 2019-04-29 09:11
 **/
@Slf4j
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理", description = "")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("")
    @ApiOperation(value = "文件上传", notes = "返回文件保存目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true)
    })
    public CommonResult importFile(MultipartFile file, HttpServletRequest request){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(fileService.importFile(file, request));
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return commonResult;
    }
}