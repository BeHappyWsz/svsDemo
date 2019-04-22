package wsz.redis.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsz.redis.common.CommonResult;
import wsz.redis.entity.User;
import wsz.redis.service.UserService;

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

    @GetMapping("")
    @ApiOperation(value = "用户获取", notes = "", response = User.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id列表",paramType = "query",dataType = "Long")
    })
    public CommonResult getUser(@RequestParam(required = false) Long[] ids){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(userService.findUser(ids));
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
            @ApiImplicitParam(name = "userName", value = "用户名", defaultValue = "", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "", required = true)
    })
    public CommonResult deleteUser(String userName, String password){
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setData(userService.deleteUser(userName, password));
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