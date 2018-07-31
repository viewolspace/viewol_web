package com.viewol.web.wx.action;

import com.alibaba.fastjson.JSON;
import com.viewol.pojo.WxUserInfo;
import com.viewol.service.IWxService;
import com.viewol.web.common.Response;
import com.viewol.web.ucard.vo.UserCardResponse;
import com.viewol.web.wx.vo.FollowResponse;
import com.viewol.web.wx.vo.TokenResponse;
import com.viewol.web.wx.vo.WxUserResponse;
import com.youguu.core.util.ClassCast;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="微信授权接口")
        }
)
@Api(value = "WxAction")
@Path( value = "wx")
@Controller("wxAction")
public class WxAction {

    @Resource
    private IWxService wxService;

    /**
     * 网页授权，获取access_token(非基础access_token)
     * 第一步：用户同意授权，获取code
     * 第二步：通过code换取网页授权access_token
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     * @param jscode
     * @return
     */
    @GET
    @Path(value = "/access_token")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "网页授权获取AccessToken", notes = "<p>第一步：用户同意授权，获取code</p><p>第二步：通过code换取网页授权access_token</p>", author = "更新于 2018-07-20")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "授权成功", response = TokenResponse.class),
            @ApiResponse(code = "0002", message = "授权失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String access_token(@ApiParam(value = "授权code", required = true) @QueryParam("jscode") String jscode) {
        TokenResponse rs = new TokenResponse();

        try{
            String json = wxService.getAccessToken(jscode);
            if(json != null && !"".equals(json)){
                TokenResponse.Token token = JSON.parseObject(json, TokenResponse.Token.class);
                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(token);
            } else {
                rs.setStatus("0002");
                rs.setMessage("授权失败");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    /**
     * 网页授权，查询用户基本信息(该接口不会返回用户是否关注公众号字段)
     * 第一步：用户同意授权，获取code
     * 第二步：通过code换取网页授权access_token
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     * @param userId 用户ID
     * @return
     */
    @GET
    @Path(value = "/userinfo")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询用户信息（已登录）", notes = "该接口不会返回用户是否关注公众号字段", author = "更新于 2018-07-23")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "查询成功", response = WxUserResponse.class),
            @ApiResponse(code = "0002", message = "查询失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String userinfo(@ApiParam(value = "用户ID", required = true) @QueryParam("userId") int userId) {
        WxUserResponse rs = new WxUserResponse();

        try{
            WxUserInfo wxUserInfo = wxService.getUserInfo(userId);
            if(wxUserInfo != null){
                WxUserResponse.WxUser wxUser = ClassCast.cast(wxUserInfo, WxUserResponse.WxUser.class);

                rs.setStatus("0000");
                rs.setMessage("查询成功");
                rs.setResult(wxUser);
            } else {
                rs.setStatus("0002");
                rs.setMessage("查询失败");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    /**
     * 网页授权，查询用户基本信息，未注册情况(该接口不会返回用户是否关注公众号字段)
     * 第一步：用户同意授权，获取code
     * 第二步：通过code换取网页授权access_token
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     * @param access_token (非基础access_token)
    *  @param openid
     * @return
     */
    @GET
    @Path(value = "/user/info")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "网页授权获取用户信息", notes = "适用未注册时调用，该接口不会返回用户是否关注公众号字段", author = "更新于 2018-07-23")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "查询成功", response = WxUserResponse.class),
            @ApiResponse(code = "0002", message = "查询失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String userinfo(@ApiParam(value = "access_token", required = true) @QueryParam("access_token") String access_token,
                           @ApiParam(value = "openid", required = true) @QueryParam("openid") String openid) {
        WxUserResponse rs = new WxUserResponse();

        try{
            WxUserInfo wxUserInfo = wxService.getUserInfo(access_token, openid);
            if(wxUserInfo != null){
                WxUserResponse.WxUser wxUser = ClassCast.cast(wxUserInfo, WxUserResponse.WxUser.class);

                rs.setStatus("0000");
                rs.setMessage("查询成功");
                rs.setResult(wxUser);
            } else {
                rs.setStatus("0002");
                rs.setMessage("查询失败");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    /**
     * 查询用户是否已关注公众号
     * @param userId 用户ID
     * @return
     */
    @GET
    @Path(value = "/isFollow")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询用户是否已关注公众号", notes = "", author = "更新于 2018-07-23")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "查询成功", response = FollowResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String isFollow(@ApiParam(value = "用户ID", required = true) @QueryParam("userId") int userId) {
        FollowResponse rs = new FollowResponse();

        try{
            boolean result = wxService.isFollow(userId);
            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(result);
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    @GET
    @Path(value = "/getSessionInfo")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "小程序授权获取openid", notes = "", author = "更新于 2018-07-20")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = UserCardResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getSessionInfo(@ApiParam(value = "授权code", required = true) @QueryParam("jscode") String jscode) {
        String result = wxService.getSessionInfo(jscode);
        return result;
    }
}
