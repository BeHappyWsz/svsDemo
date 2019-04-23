package wsz.redis.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsz.redis.common.CommonResult;
import wsz.redis.service.UserService;
import wsz.redis.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 用户管理
 * @author: WSZ
 * @create: 2019-04-18 14:22
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理", description = "")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/page")
    public CommonResult getUser(@RequestBody UserVo vo){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(userService.findByPage(vo));
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return  commonResult;
    }

    @GetMapping("login")
    @ApiOperation(value = "用户登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "", required = true)
    })
    public CommonResult login(String username, String password, HttpServletRequest request){
        CommonResult commonResult = new CommonResult();
        try {
            request.getSession().setAttribute("aa", request.getRequestURI());
            commonResult.setData(userService.login(username, password));
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return  commonResult;
    }

    @PostMapping("")
    @ApiOperation(value = "用户注册", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, allowableValues = "11,22,33,44,55"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)
    })
    public CommonResult register(String userName, String password){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(userService.registerUser(userName, password));
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return  commonResult;
    }

    @PutMapping("")
    @ApiOperation(value = "用户更新", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", defaultValue = "", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "", required = true)
    })
    public CommonResult update(String userName, String password){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(userService.update(userName, password));
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return  commonResult;
    }

    @DeleteMapping("")
    @ApiOperation(value = "用户删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户主键", required = true)
    })
    public CommonResult deleteUser(Long uid){
        CommonResult commonResult = new CommonResult();
        try {
            userService.deleteUser(uid);
            commonResult.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(503);
            commonResult.setErrMsg(e.getMessage());
            log.error("error:{}", e.getMessage());
        }
        return  commonResult;
    }
}