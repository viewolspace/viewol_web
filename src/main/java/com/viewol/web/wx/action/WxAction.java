package com.viewol.web.wx.action;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.viewol.pojo.*;
import com.viewol.service.*;
import com.viewol.util.Base64Img;
import com.viewol.web.common.Response;
import com.viewol.web.company.vo.ErCodeResponse;
import com.viewol.web.wx.util.WechatMessageUtil;
import com.viewol.web.wx.vo.BuserLoginResponse;
import com.viewol.web.wx.vo.FollowResponse;
import com.viewol.web.wx.vo.LoginResponse;
import com.viewol.web.wx.vo.WxJsapiSignatureVO;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.File;
import java.util.Map;

@SwaggerDefinition(
        tags = {
                @Tag(name = "v1.0", description = "微信授权接口")
        }
)
@Api(value = "WxAction")
@Path(value = "wx")
@Controller("wxAction")
public class WxAction {
    private static Log log = LogFactory.getLog(WxAction.class);

    @Resource
    private IWxService wxService;
    @Resource
    private IFUserService fUserService;
    @Resource
    private IBUserService bUserService;
    @Resource
    private IUserSessionService userSessionService;
    @Resource
    private ICompanyService companyService;

    /**
     * 验证token
     *
     * @return
     */

    @GET
    @Path(value = "/handler")
    @Produces("text/html;charset=UTF-8")
    public String handler(@QueryParam("signature") String signature,
                          @QueryParam("timestamp") String timestamp,
                          @QueryParam("nonce") String nonce,
                          @QueryParam("echostr") String echostr) {




        if (echostr != null) return echostr;
        return "";
    }

    /**
     * 在微信用户和公众号产生交互的过程中，
     * 用户的某些操作会使得微信服务器通过事件推送的形式通知到开发者在开发者中心处设置的服务器地址，从而开发者可以获取到该信息。
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param request
     * @return
     */
    @POST
    @Path(value = "/handler")
    @Produces("text/html;charset=UTF-8")
    public String handler(@QueryParam("signature") String signature,
                          @QueryParam("timestamp") String timestamp,
                          @QueryParam("nonce") String nonce,
                          @QueryParam("echostr") String echostr,
                          @Context HttpServletRequest request) {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
        String event = map.get("Event");
        log.info("data:{}",map.toString());
        String openId = map.get("FromUserName");
//        String eventKey;
        try{
            switch (event){
                case WechatMessageUtil.MESSAGE_EVENT_SUBSCRIBE:
                    log.info("用户关注:{}",openId);
                    FUser fUser = fUserService.getUserByOpenId(openId,FUserBind.TYPE_WEIXIN);
                    if(fUser==null){
                        WxMpUser wm = wxService.getUserInfo(openId);
                        log.info("WxMpUser:{}",wm);
                        if(wm != null ){
                            if(wm.getSubscribe()){
                                fUser = fUserService.getUserByUuid(wm.getUnionId());
                                if(fUser==null){
                                    fUser = new FUser();
                                    fUser.setUuid(wm.getUnionId());
                                    fUser.setHeadImgUrl(wm.getHeadImgUrl());
                                    int result = fUserService.addFUser(fUser, openId, wm.getUnionId(), FUserBind.TYPE_WEIXIN);
                                }else{
                                    fUserService.addFuserBind(fUser.getUserId(),openId, wm.getUnionId(), FUserBind.TYPE_WEIXIN);
                                }


                                log.info("添加用户:{}",openId);
                            }
                        }
                    }else{
                        log.info("用户已经存在:{}",openId);
                    }
                    break;
                case  WechatMessageUtil.MESSAGE_EVENT_SCAN:

                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return "success";
    }


    private LoginResponse.UserInfo getUserInfo(FUser fuser,LoginResponse rs){
        LoginResponse.UserInfo userInfo = rs.new UserInfo();
        userInfo.setUserId(fuser.getUserId());
        userInfo.setUserName(fuser.getUserName());
        userInfo.setPhone(fuser.getPhone());
        userInfo.setCompany(fuser.getCompany());
        userInfo.setPosition(fuser.getPosition());
        userInfo.setEmail(fuser.getEmail());
        userInfo.setAge(fuser.getAge());
        userInfo.setHeadImgUrl(fuser.getHeadImgUrl());
        userInfo.setCompanyId(fuser.getCompanyId());

        String sessionId = userSessionService.saveSession(fuser.getUserId(), UserSession.TYPE_MA);
        userInfo.setSessionId(sessionId);
        return userInfo;
    }

    /**
     * 小程序自动登录
     *
     * @param code 临时授权code
     * @return
     */
    @GET
    @Path(value = "/maLogin")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "小程序自动登录,请使用maPhoneLogin接口替换此接口", notes = "", author = "更新于 2018-08-02")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = LoginResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String maLogin(@ApiParam(value = "哪个小程序 1 观展通  2 观展讯", required = true) @QueryParam("maNum") @DefaultValue("1") int maNum,
                            @ApiParam(value = "授权code", required = true) @QueryParam("code") String code,
                          @ApiParam(value = "消息密文,不需要获取微信用户信息可以不传", required = true) @QueryParam("encryptedData") String encryptedData,
                          @ApiParam(value = "加密算法的初始向量,不需要获取微信用户信息可以不传", required = true) @QueryParam("ivStr") String ivStr) {

        LoginResponse rs = new LoginResponse();

        try {
            if(encryptedData == null || ivStr == null || "".equals(encryptedData) || "".equals(ivStr)){
                return YouguuJsonHelper.returnJSON("0002", "encryptedData不能为空");
            }

            WxMaJscode2SessionResult wxMaJscode2Session = wxService.getSessionInfo(maNum,code);
            if (wxMaJscode2Session == null) {
                return YouguuJsonHelper.returnJSON("0002", "授权失败");
            }

            String sessionKey = wxMaJscode2Session.getSessionKey();
            String openid = wxMaJscode2Session.getOpenid();
            String unionid = wxMaJscode2Session.getUnionid();

            //已注册，返回用户信息
            FUser fuser = fUserService.getUserByOpenId(openid,maNum);
//            FUser fuser = fUserService.getUserByUuid(unionid);
            if (fuser != null) {

                LoginResponse.UserInfo userInfo = this.getUserInfo(fuser,rs);
                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }

            //可能是新用户
            WxMaUserInfo wxMaUserInfo = wxService.getUserInfo(maNum,sessionKey, encryptedData, ivStr);
            //先使用uuid查询一下
            fuser = fUserService.getUserByUuid(wxMaUserInfo.getUnionId());
            if(fuser!=null){
                fUserService.addFuserBind(fuser.getUserId(),openid, wxMaUserInfo.getUnionId(), maNum);
                LoginResponse.UserInfo userInfo = this.getUserInfo(fuser,rs);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }

            //注册新用户
            fuser = new FUser();
            if (wxMaUserInfo != null) {
                fuser.setHeadImgUrl(wxMaUserInfo.getAvatarUrl());
            }
            fuser.setUuid(wxMaUserInfo.getUnionId());

            int result = fUserService.addFUser(fuser, openid, wxMaUserInfo.getUnionId(), maNum);

            if (result > 0) {
                LoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(result);
                userInfo.setHeadImgUrl(fuser.getHeadImgUrl());

                String sessionId = userSessionService.saveSession(fuser.getUserId(), UserSession.TYPE_MA);
                userInfo.setSessionId(sessionId);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
            } else {
                rs.setStatus("0004");
                rs.setMessage("注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }



    /**
     * 小程序授权手机号登录
     *
     * @param code 临时授权code
     * @return
     */
    @GET
    @Path(value = "/maPhoneLogin")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "小程序自动登录", notes = "", author = "更新于 2019-09-07")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = LoginResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String maPhoneLogin(@ApiParam(value = "哪个小程序 1 观展通  2 观展讯", required = true) @QueryParam("maNum") @DefaultValue("1") int maNum,
                                @ApiParam(value = "授权code", required = true) @QueryParam("code") String code,
                               @ApiParam(value = "昵称", required = true) @QueryParam("nickName") String nickName,
                               @ApiParam(value = "头像", required = true) @QueryParam("headPic") String headPic,
                          @ApiParam(value = "消息密文,不需要获取微信用户信息可以不传", required = true) @QueryParam("encryptedData") String encryptedData,
                          @ApiParam(value = "加密算法的初始向量,不需要获取微信用户信息可以不传", required = true) @QueryParam("ivStr") String ivStr) {

        LoginResponse rs = new LoginResponse();

        try {
            if(encryptedData == null || ivStr == null || "".equals(encryptedData) || "".equals(ivStr)){
                return YouguuJsonHelper.returnJSON("0002", "encryptedData不能为空");
            }

            WxMaJscode2SessionResult wxMaJscode2Session = wxService.getSessionInfo(maNum,code);
            if (wxMaJscode2Session == null) {
                return YouguuJsonHelper.returnJSON("0002", "授权失败");
            }

            String sessionKey = wxMaJscode2Session.getSessionKey();
            String openid = wxMaJscode2Session.getOpenid();
            String unionid = wxMaJscode2Session.getUnionid();

            //已注册，返回用户信息
            FUser fuser = fUserService.getUserByOpenId(openid,maNum);
//            FUser fuser = fUserService.getUserByUuid(unionid);
            if (fuser != null) {

                LoginResponse.UserInfo userInfo = this.getUserInfo(fuser,rs);
                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }


            //先使用uuid查询一下
            fuser = fUserService.getUserByUuid(unionid);
            if(fuser!=null){
                fUserService.addFuserBind(fuser.getUserId(),openid, unionid, maNum);
                LoginResponse.UserInfo userInfo = this.getUserInfo(fuser,rs);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }
            //可能是新用户
            WxMaPhoneNumberInfo phoneNumberInfo = wxService.getPhoneNumberInfo(maNum,sessionKey, encryptedData, ivStr);
            //注册新用户
            fuser = new FUser();
            fuser.setUserName(nickName);
            fuser.setHeadImgUrl(headPic);
            fuser.setUuid(unionid);
            fuser.setPhone(phoneNumberInfo.getPhoneNumber());

            int result = fUserService.addFUser(fuser, openid, unionid, maNum);

            if (result > 0) {
                LoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(result);
                userInfo.setHeadImgUrl(fuser.getHeadImgUrl());

                String sessionId = userSessionService.saveSession(fuser.getUserId(), UserSession.TYPE_MA);
                userInfo.setSessionId(sessionId);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
            } else {
                rs.setStatus("0004");
                rs.setMessage("注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    /**
     * H5自动授权登录
     *
     * @param jscode 临时授权code
     * @return
     */
    @GET
    @Path(value = "/mpLogin")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "H5自动授权登录", notes = "H5自动授权登录", author = "更新于 2018-08-02")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "授权成功", response = LoginResponse.class),
            @ApiResponse(code = "0002", message = "授权失败", response = Response.class),
            @ApiResponse(code = "0003", message = "获取第三方数据失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String mpLogin(@ApiParam(value = "临时授权code", required = true) @QueryParam("jscode") String jscode) {
        LoginResponse rs = new LoginResponse();

        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.getAccessToken(jscode);
            if (wxMpOAuth2AccessToken == null) {
                return YouguuJsonHelper.returnJSON("0002", "授权失败");
            }

            String accessToken = wxMpOAuth2AccessToken.getAccessToken();
            String openid = wxMpOAuth2AccessToken.getOpenId();
            String unionId = wxMpOAuth2AccessToken.getUnionId();

            //已注册，返回用户信息
            FUser fuser = fUserService.getUserByUuid(unionId);
            if (fuser != null) {
                LoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(fuser.getUserId());
                userInfo.setUserName(fuser.getUserName());
                userInfo.setPhone(fuser.getPhone());
                userInfo.setCompany(fuser.getCompany());
                userInfo.setPosition(fuser.getPosition());
                userInfo.setEmail(fuser.getEmail());
                userInfo.setAge(fuser.getAge());
                userInfo.setHeadImgUrl(fuser.getHeadImgUrl());
                userInfo.setCompanyId(fuser.getCompanyId());

                String sessionId = userSessionService.saveSession(fuser.getUserId(), UserSession.TYPE_MA);
                userInfo.setSessionId(sessionId);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }

            WxMpUser wxMpUser = wxService.getUserInfo(accessToken, openid);
            if (wxMpUser == null) {
                return YouguuJsonHelper.returnJSON("0003", "获取第三方数据失败");
            }

            fuser = new FUser();
            fuser.setHeadImgUrl(wxMpUser.getHeadImgUrl());
            fuser.setUuid(unionId);

            int result = fUserService.addFUser(openid, unionId, FUserBind.TYPE_WEIXIN);

            if (result > 0) {
                LoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(result);
                userInfo.setHeadImgUrl(fuser.getHeadImgUrl());

                String sessionId = userSessionService.saveSession(fuser.getUserId(), UserSession.TYPE_MA);
                userInfo.setSessionId(sessionId);

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
            } else {
                rs.setStatus("0004");
                rs.setMessage("注册失败");
            }
        } catch (Exception e) {
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }

    /**
     * 查询用户是否已关注公众号
     *
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

        try {
            boolean result = wxService.isFollow(userId);
            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(result);
        } catch (Exception e) {
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }


    /**
     * 展商业务员扫码登录接口
     *
     * @param jscode
     * @return
     */
    @GET
    @Path(value = "/bUserLogin")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "展商业务员扫码登录接口", notes = "展商业务员通过扫描展商管理后台的二维码，获取H5访问权限。", author = "更新于 2018-08-02")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "授权成功", response = BuserLoginResponse.class),
            @ApiResponse(code = "0002", message = "授权失败", response = Response.class),
            @ApiResponse(code = "0003", message = "获取第三方数据失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String bUserLogin(@ApiParam(value = "临时授权code", required = true) @QueryParam("jscode") String jscode,
                             @ApiParam(value = "展商ID", required = true) @QueryParam("companyId") int companyId) {
        BuserLoginResponse rs = new BuserLoginResponse();

        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.getAccessToken(jscode);
            if (wxMpOAuth2AccessToken == null) {
                return YouguuJsonHelper.returnJSON("0002", "授权失败");
            }

            String accessToken = wxMpOAuth2AccessToken.getAccessToken();
            String openid = wxMpOAuth2AccessToken.getOpenId();
            String unionId = wxMpOAuth2AccessToken.getUnionId();

            Company company = companyService.getCompany(companyId);
            //业务员已注册，返回业务员信息
            BUser bUser = bUserService.getBUser(openid);
            if (bUser != null) {
                BuserLoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(bUser.getUserId());
                userInfo.setUserName(bUser.getUserName());
                userInfo.setPhone(bUser.getPhone());
                userInfo.setPosition(bUser.getPosition());
                userInfo.setHeadImgUrl(bUser.getHeadImgUrl());
                userInfo.setCompanyId(bUser.getCompanyId());
                userInfo.setStatus(bUser.getStatus());
                userInfo.setCompanyLogo(company.getLogoView());
                userInfo.setCompanyName(company.getName());
                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
                return rs.toJSONString();
            }

            //注册
            WxMpUser wxMpUser = wxService.getUserInfo(accessToken, openid);
            if (wxMpUser == null) {
                return YouguuJsonHelper.returnJSON("0003", "获取第三方数据失败");
            }

            bUser = new BUser();
            bUser.setCompanyId(companyId);
            bUser.setOpenId(openid);
            bUser.setUuid(unionId);
            bUser.setHeadImgUrl(wxMpUser.getHeadImgUrl());
            bUser.setStatus(BUser.STATUS_TRIAL);//0-待审

            int result = bUserService.addBUser(bUser);

            if (result > 0) {
                BuserLoginResponse.UserInfo userInfo = rs.new UserInfo();
                userInfo.setUserId(result);
                userInfo.setHeadImgUrl(bUser.getHeadImgUrl());
                userInfo.setStatus(BUser.STATUS_TRIAL);
                userInfo.setUserName(bUser.getUserName());
                userInfo.setPhone(bUser.getPhone());
                userInfo.setPosition(bUser.getPosition());
                userInfo.setCompanyId(bUser.getCompanyId());
                userInfo.setCompanyLogo(company.getLogoView());
                userInfo.setCompanyName(company.getName());

                rs.setStatus("0000");
                rs.setMessage("授权成功");
                rs.setResult(userInfo);
            } else {
                rs.setStatus("0004");
                rs.setMessage("注册失败");
            }
        } catch (Exception e) {
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }

        return rs.toJSONString();
    }




    @GET
    @Path(value = "/jsapiSignature")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "创建调用jsapi时所需要的签名", notes = "", author = "更新于 2018-07-23")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "查询成功", response = WxJsapiSignatureVO.class),
            @ApiResponse(code = "0001", message = "系统异常", response = WxJsapiSignatureVO.class)
    })
    public String jsapiSignature(@ApiParam(value = "url", required = true) @QueryParam("url") String url) {
        JSONObject json = new JSONObject();
        json.put("status","0000");
        json.put("message","ok");
        WxJsapiSignature wxJsapiSignature = wxService.createJsapiSignature(url);
        json.put("result",wxJsapiSignature);
        return json.toJSONString();
    }


    @GET
    @Path(value = "/getPublicxaCode")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取小程序二维码", notes = "", author = "更新于 2018-07-23")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "查询成功", response = WxJsapiSignatureVO.class),
            @ApiResponse(code = "0001", message = "系统异常", response = WxJsapiSignatureVO.class)
    })
    public String getPublicxaCode(@ApiParam(value = "哪个小程序 1 观展通  2 观展讯", required = true) @QueryParam("maNum") @DefaultValue("1") int maNum,
                                  @ApiParam(value = "page", required = true) @QueryParam("page") String page,
                                  @ApiParam(value = "scene", required = true) @QueryParam("scene") String scene,
                                  @ApiParam(value = "width", required = true) @QueryParam("width") int width) {
        ErCodeResponse rs = new ErCodeResponse();

        try {
            File file = wxService.createPublicxaCode(maNum,page,scene,width);

            String base64Str = Base64Img.GetImageStrFromPath(file.getPath());
            if(file!=null){
                rs.setStatus("0000");
                rs.setMessage("成功");
                rs.setErcode(base64Str);
            } else {
                rs.setStatus("0001");
                rs.setMessage("系统异常");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            e.printStackTrace();
        }


        return rs.toJSONString();
    }


    /**
     * H5获取openId
     *
     * @param jscode 临时授权code
     * @return
     */
    @GET
    @Path(value = "/getOpenId")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "H5获取openId", notes = "H5获取openId", author = "更新于 2018-08-02")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "授权成功", response = LoginResponse.class),
            @ApiResponse(code = "0002", message = "授权失败", response = Response.class),
            @ApiResponse(code = "0003", message = "获取第三方数据失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getOpenId(@ApiParam(value = "临时授权code", required = true) @QueryParam("jscode") String jscode) {
        JSONObject json = new JSONObject();

        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.getAccessToken(jscode);
            if (wxMpOAuth2AccessToken == null) {
                return YouguuJsonHelper.returnJSON("0002", "授权失败");
            }

            String accessToken = wxMpOAuth2AccessToken.getAccessToken();
            String openid = wxMpOAuth2AccessToken.getOpenId();
            String unionId = wxMpOAuth2AccessToken.getUnionId();
            WxMpUser wxMpUser = wxService.getUserInfo(accessToken, openid);
            json.put("status","0000");
            json.put("openid", openid);
            json.put("pic", wxMpUser.getHeadImgUrl());



        }catch (Exception e){
            json.put("status","0001");
            json.put("msg","错误");
        }
        return json.toJSONString();
    }
}
