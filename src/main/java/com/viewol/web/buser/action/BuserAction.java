package com.viewol.web.buser.action;

import com.viewol.pojo.BUser;
import com.viewol.service.IBUserService;
import com.viewol.web.buser.vo.BUserResponse;
import com.viewol.web.common.Response;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;

/**
 * Created by lenovo on 2018/8/17.
 */
@SwaggerDefinition(
        tags = {
                @Tag(name = "v1.0", description = "业务员信息维护")
        }
)
@Api(value = "BuserAction")
@Path(value = "buser")
@Controller("buserAction")
public class BuserAction {
    @Resource
    private IBUserService ibUserService;


    @GET
    @Path(value = "/getBuser")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询我的基本信息", notes = "客户进入“我的”页面，显示自己的个人信息。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = BUserResponse.class),
            @ApiResponse(code = "0002", message = "用户不存在", response = BUserResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getBuser(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId) {

        if (userId <= 0) {
            return YouguuJsonHelper.returnJSON("0002", "userId错误 ， 用户不存在");
        }

        BUser user = ibUserService.getBUser(userId);

        if (user == null) {
            return YouguuJsonHelper.returnJSON("0002", "用户不存在");
        }

        return YouguuJsonHelper.returnJSON("0000", "ok", user);
    }


    @POST
    @Path(value = "/updateBuser")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "修改基本信息", notes = "修改基本信息", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "修改成功", response = BUserResponse.class),
            @ApiResponse(code = "0013", message = "修改失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String updateBuser(@ApiParam(value = "业务员ID", required = true) @FormParam("userId") int userId,
                              @ApiParam(value = "业务员姓名", required = true) @FormParam("userName") String userName,
                              @ApiParam(value = "业务员姓名", required = true) @FormParam("position") String position,
                              @ApiParam(value = "业务员姓名", required = true) @FormParam("phone") String phone) {

        try{
            if (userId <= 0) {
                return YouguuJsonHelper.returnJSON("0002", "userId错误 ， 用户不存在");
            }

            BUser user = ibUserService.getBUser(userId);

            if (user == null) {
                return YouguuJsonHelper.returnJSON("0002", "用户不存在");
            }

            user.setPhone(phone);
            user.setPosition(position);
            user.setUserName(userName);

            int result = ibUserService.upDateBUser(user);
            if(result>0){
                user = ibUserService.getBUser(userId);
                return YouguuJsonHelper.returnJSON("0000", "ok", user);
            }

            return YouguuJsonHelper.returnJSON("0000", "修改失败");
        } catch (Exception e){
            return YouguuJsonHelper.returnJSON("0001", "系统异常");
        }
    }
}
