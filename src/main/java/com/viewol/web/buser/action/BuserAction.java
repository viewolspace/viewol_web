package com.viewol.web.buser.action;

import com.viewol.pojo.BUser;
import com.viewol.pojo.BbsJoin;
import com.viewol.service.IBUserService;
import com.viewol.service.IBbsJoinService;
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

    @Resource
    private IBbsJoinService bbsJoinService;


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



    @POST
    @Path(value = "/userJoinBbs")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "用户报名论坛", notes = "用户报名论坛", author = "更新于 2019-10-09")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "报名成功", response = BUserResponse.class),
            @ApiResponse(code = "0013", message = "已经报名，请勿重复报名", response = Response.class),
            @ApiResponse(code = "0001", message = "其他异常，直接显示文案就可以", response = Response.class)
    })
    public String userJoinBbs(@ApiParam(value = "姓名", required = true) @FormParam("name") String name,
                              @ApiParam(value = "性别", required = true) @FormParam("sex") String sex,
                              @ApiParam(value = "单位", required = true) @FormParam("company") String company,
                              @ApiParam(value = "职位", required = true) @FormParam("position") String position,
                              @ApiParam(value = "手机", required = true) @FormParam("phone") String phone,
                              @ApiParam(value = "邮箱", required = true) @FormParam("email") String email,
                              @ApiParam(value = "意见", required = true) @FormParam("idea") String idea,
                              @ApiParam(value = "论坛id", required = true) @FormParam("bbsId") int bbsId,
                              @ApiParam(value = "地址", required = true) @FormParam("address") String address) {

        try{
            if(name==null || "".equals(name)
                    || company==null || "".equals(company)
                    || position==null || "".equals(position)
                    || phone==null || "".equals(phone)){
                return YouguuJsonHelper.returnJSON("0001", "请填写必填的内容");
            }
            BbsJoin bbsJoin = bbsJoinService.getBbsJoin(phone,bbsId);
            if(bbsJoin!=null){
                return YouguuJsonHelper.returnJSON("0013", "已经报名，请勿重复报名");
            }


            bbsJoin = new BbsJoin();
            bbsJoin.setName(name);
            bbsJoin.setSex(sex);
            bbsJoin.setCompany(company);
            bbsJoin.setPosition(position);
            bbsJoin.setPhone(phone);
            bbsJoin.setEmail(email);
            bbsJoin.setIdea(idea);
            bbsJoin.setBbsId(bbsId);
            bbsJoin.setAddress(address);
            int result  = bbsJoinService.addBbsJoin(bbsJoin);
            if(result>0){
                return YouguuJsonHelper.returnJSON("0000", "报名成功");
            }else{
                return YouguuJsonHelper.returnJSON("0001", "请稍后尝试");
            }


        } catch (Exception e){
            return YouguuJsonHelper.returnJSON("0001", "系统异常");
        }
    }


    @POST
    @Path(value = "/userSignBbs")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "用户报名论坛", notes = "用户报名论坛", author = "更新于 2019-10-09")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "签到成功", response = BUserResponse.class),
            @ApiResponse(code = "0013", message = "还未报名，请先报名", response = Response.class),
            @ApiResponse(code = "0001", message = "其他异常，直接显示文案就可以", response = Response.class)
    })
    public String userSignBbs(
                              @ApiParam(value = "手机", required = true) @FormParam("phone") String phone,
                              @ApiParam(value = "论坛id", required = true) @FormParam("bbsId") int bbsId) {

        try{
            if(phone==null || "".equals(phone)){
                return YouguuJsonHelper.returnJSON("0001", "请输入手机号");
            }
            BbsJoin bbsJoin = bbsJoinService.getBbsJoin(phone,bbsId);
            if(bbsJoin==null){
                return YouguuJsonHelper.returnJSON("0013", "还未报名，请先报名");
            }
            int result  = bbsJoinService.signIn(phone,bbsJoin.getId());
            if(result>0){
                return YouguuJsonHelper.returnJSON("0000", "签到成功");
            }else{
                return YouguuJsonHelper.returnJSON("0001", "请稍后尝试");
            }


        } catch (Exception e){
            return YouguuJsonHelper.returnJSON("0001", "系统异常");
        }
    }

}
