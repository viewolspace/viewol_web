package com.viewol.web.fuser.action;

import com.alibaba.fastjson.JSON;
import com.viewol.pojo.FUser;
import com.viewol.pojo.UserBrowse;
import com.viewol.pojo.UserCollection;
import com.viewol.pojo.UserDownload;
import com.viewol.service.*;
import com.viewol.sms.ISmsService;
import com.viewol.sms.QingSmsServiceImpl;
import com.viewol.web.common.Response;
import com.viewol.web.fuser.vo.FUserResponse;
import com.viewol.web.fuser.vo.UserBrowseResponse;
import com.viewol.web.fuser.vo.UserCollectionResponse;
import com.viewol.web.fuser.vo.UserDownloadResponse;
import com.viewol.web.ucard.util.SecurityCode;
import com.viewol.web.ucard.util.SecurityImage;
import com.viewol.web.ucard.vo.ImageRandVO;
import com.youguu.core.util.RedisUtil;
import com.youguu.core.util.redis.RedisPool;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@SwaggerDefinition(
        tags = {
                @Tag(name = "v1.0", description = "个人信息")
        }
)
@Api(value = "FuserAction")
@Path(value = "fuser")
@Controller("fuserAction")
public class FuserAction {

    private static String IMAGE_KRY = "imageCode:%s";

    private static String PHONE_KRY = "phoneCode:%s";

    @Resource
    private IFUserService fUserService;
    @Resource
    private IProductService productService;
    @Resource
    private IUserCollectionService userCollectionService;
    @Resource
    private IUserDownloadService userDownloadService;
    @Resource
    private IUserBrowseService userBrowseService;

    /**
     * 查询我的基本信息
     *
     * @param userId
     * @return
     */
    @GET
    @Path(value = "/getFuser")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询我的基本信息", notes = "客户进入“我的”页面，显示自己的个人信息。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = FUserResponse.class),
            @ApiResponse(code = "0002", message = "用户不存在", response = FUserResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getFuser(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId) {
        try {
            FUserResponse rs = new FUserResponse();
            FUser fUser = fUserService.getFuser(userId);
            if(null == fUser){
                rs.setStatus("0002");
                rs.setMessage("用户不存在");
                return rs.toJSONString();
            }

            FUserResponse.FUserVO vo = rs.new FUserVO();
            vo.setUserId(fUser.getUserId());
            vo.setUserName(fUser.getUserName());
            vo.setPhone(fUser.getPhone());
            vo.setCompany(fUser.getCompany());
            vo.setPosition(fUser.getPosition());
            vo.setEmail(fUser.getEmail());
            vo.setAge(fUser.getAge());
            vo.setCreateTime(fUser.getcTime());
            vo.setCompanyId(fUser.getCompanyId());

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(vo);

            return rs.toJSONString();
        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }



    /**
     * 查询我的基本信息
     *
     * @param userId
     * @return
     */
    @GET
    @Path(value = "/isPerfectnfo")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "是否完善资料", notes = "是否完善资料。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "信息已经完善", response = Response.class),
            @ApiResponse(code = "0002", message = "信息未完善", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String isPerfectnfo(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId) {
        try {
            Response rs = new Response();
            if(userId<=0){
                rs.setStatus("0001");
                rs.setMessage("用户id错误");
                return rs.toJSONString();
            }

            FUser fUser = fUserService.getFuser(userId);
            if(null == fUser){
                rs.setStatus("0001");
                rs.setMessage("用户不存在");
                return rs.toJSONString();
            }

            if(fUser.getPhone() == null || "".equals(fUser.getPhone())){
                rs.setStatus("0002");
                rs.setMessage("信息未完善");
                return rs.toJSONString();
            }

            rs.setStatus("0000");
            rs.setMessage("信息已经完善");

            return rs.toJSONString();
        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }

    /**
     * 修改个人信息
     *
     * @param userId    用户ID
     * @param phone     手机号
     * @param company   公司名称
     * @param companyId 公司ID
     * @param position  职位
     * @param email     邮箱
     * @param age       年龄
     * @return
     */
    @POST
    @Path(value = "/updateUser")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "修改个人信息", notes = "客户进入“我的”页面，可以修改自己的基本信息。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "修改成功", response = Response.class),
            @ApiResponse(code = "0002", message = "用户不存在", response = Response.class),
            @ApiResponse(code = "0003", message = "修改失败", response = Response.class),
            @ApiResponse(code = "0004", message = "验证码错误", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String updateUser(@ApiParam(value = "验证码", required = true) @FormParam("rand") String rand,
                             @ApiParam(value = "客户ID", required = true) @FormParam("userId") int userId,
                             @ApiParam(value = "客户姓名", required = true) @FormParam("userName") String userName,
                             @ApiParam(value = "手机号", required = true) @FormParam("phone") String phone,
                             @ApiParam(value = "公司", required = true) @FormParam("company") String company,
                             @ApiParam(value = "邀请展商ID") @FormParam("companyId") int companyId,
                             @ApiParam(value = "职位", required = true) @FormParam("position") String position,
                             @ApiParam(value = "邮箱", required = true) @FormParam("email") String email,
                             @ApiParam(value = "年龄", required = true) @FormParam("age") int age) {
        try {
            Response rs = new Response();

            RedisPool redis = RedisUtil.getRedisPool("user");

            String phoneKey = String.format(PHONE_KRY, phone);

            String rand_s = redis.get(phoneKey);

            if(rand==null || !rand.equals(rand_s)){
                rs.setStatus("0004");
                rs.setMessage("验证码错误");
                return rs.toJSONString();
            }


            FUser fUser = fUserService.getFuser(userId);
            if(null == fUser){
                rs.setStatus("0002");
                rs.setMessage("用户不存在");
                return rs.toJSONString();
            }

            fUser.setUserName(userName);
            fUser.setPhone(phone);
            fUser.setCompany(company);
            if(companyId>0){
                fUser.setCompanyId(companyId);
            }

            fUser.setPosition(position);
            fUser.setEmail(email);
            fUser.setAge(age);

            int result = fUserService.updateUser(fUser);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("修改成功");
            } else {
                rs.setStatus("0003");
                rs.setMessage("修改失败");
            }

            return rs.toJSONString();
        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }



    /**
     * 修改个人信息
     *
     * @param userId    用户ID
     * @param company   公司名称
     * @param companyId 公司ID
     * @param position  职位
     * @param email     邮箱
     * @param age       年龄
     * @return
     */
    @POST
    @Path(value = "/updateUserNoPhone")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "修改个人信息(不修改手机号码)", notes = "修改个人信息(不修改手机号码。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "修改成功", response = Response.class),
            @ApiResponse(code = "0002", message = "用户不存在", response = Response.class),
            @ApiResponse(code = "0003", message = "修改失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String updateUserNoPhone(
                             @ApiParam(value = "客户ID", required = true) @FormParam("userId") int userId,
                             @ApiParam(value = "客户姓名", required = true) @FormParam("userName") String userName,
                             @ApiParam(value = "公司", required = true) @FormParam("company") String company,
                             @ApiParam(value = "邀请展商ID") @FormParam("companyId") int companyId,
                             @ApiParam(value = "职位", required = true) @FormParam("position") String position,
                             @ApiParam(value = "邮箱", required = true) @FormParam("email") String email,
                             @ApiParam(value = "年龄", required = true) @FormParam("age") int age) {
        try {
            Response rs = new Response();




            FUser fUser = fUserService.getFuser(userId);
            if(null == fUser){
                rs.setStatus("0002");
                rs.setMessage("用户不存在");
                return rs.toJSONString();
            }

            fUser.setUserName(userName);
            fUser.setCompany(company);
            if(companyId>0){
                fUser.setCompanyId(companyId);
            }

            fUser.setPosition(position);
            fUser.setEmail(email);
            fUser.setAge(age);

            int result = fUserService.updateUser(fUser);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("修改成功");
            } else {
                rs.setStatus("0003");
                rs.setMessage("修改失败");
            }

            return rs.toJSONString();
        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }

    /**
     * 个人信息录入+问卷调查
     *
     * @param phone         手机号
     * @param company       公司名称
     * @param companyId     公司ID
     * @param position      职位
     * @param email         邮箱
     * @param age           年龄
     * @param questionOne   问题1答案
     * @param questionTwo   问题2答案
     * @param questionThree 问题3答案
     * @return
     */
    @POST
    @Path(value = "/addFUser")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "个人信息录入(H5)", notes = "用户在H5页面填写基本信息，完成调查问卷，进行提交，该H5页面支持名片扫描。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "保存成功", response = Response.class),
            @ApiResponse(code = "0003", message = "保存失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String addFUser(@ApiParam(value = "手机号", required = true) @FormParam("phone") String phone,
                           @ApiParam(value = "客户姓名", required = true) @FormParam("userName") String userName,
                           @ApiParam(value = "公司", required = true) @FormParam("company") String company,
                           @ApiParam(value = "邀请展商ID") @FormParam("companyId") int companyId,
                           @ApiParam(value = "职位", required = true) @FormParam("position") String position,
                           @ApiParam(value = "邮箱", required = true) @FormParam("email") String email,
                           @ApiParam(value = "年龄", required = true) @FormParam("age") int age,
                           @ApiParam(value = "答案1", required = true) @FormParam("questionOne") String questionOne,
                           @ApiParam(value = "答案2", required = true) @FormParam("questionTwo") String questionTwo,
                           @ApiParam(value = "答案3", required = true) @FormParam("questionThree") String questionThree,
                           @ApiParam(value = "微信open_id", required = true) @FormParam("openId") String openId,
                           @ApiParam(value = "微信uuid", required = true) @FormParam("uuid") String uuid,
                           @ApiParam(value = "注册类型，1-小程序；2-公众号", required = true) @FormParam("type") int type) {

        try {
            Response rs = new Response();
            FUser fUser = new FUser();

            fUser.setUserName(userName);
            fUser.setPhone(phone);
            fUser.setCompany(company);
            if(companyId>0){
                fUser.setCompanyId(companyId);
            }

            fUser.setPosition(position);
            fUser.setEmail(email);
            fUser.setAge(age);

            StringBuffer answer = new StringBuffer();
            answer.append(questionOne).append(";");
            answer.append(questionTwo).append(";");
            answer.append(questionThree).append(";");

            int result = fUserService.addFUser(fUser, answer.toString(), openId, uuid, type);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("保存成功");
            } else {
                rs.setStatus("0003");
                rs.setMessage("保存失败");
            }

            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }

    /**
     * 添加收藏
     *
     * @param userId  用户ID
     * @param type    1-展商收藏；2-产品收藏
     * @param thirdId 展商id(产品id)
     * @return
     */
    @POST
    @Path(value = "/addUserCollection")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "添加收藏接口", notes = "可以通过该接口可以收藏展商(产品)，收藏后在“我的”页面可以查看。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "收藏成功", response = Response.class),
            @ApiResponse(code = "0003", message = "收藏失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String addUserCollection(@ApiParam(value = "客户ID", required = true) @FormParam("userId") int userId,
                                    @ApiParam(value = "收藏类型，1-展商收藏；2-产品收藏", required = true) @FormParam("type") int type,
                                    @ApiParam(value = "展商id(产品id)", required = true) @FormParam("thirdId") int thirdId) {
       try {
           Response rs = new Response();


           int result = userCollectionService.isCollection(userId, type, thirdId);
           if(result>0){
               rs.setStatus("0000");
               rs.setMessage("已经收藏");
           }else{
               result = userCollectionService.addUserCollection(userId, type, thirdId);
               if(result>0){
                   rs.setStatus("0000");
                   rs.setMessage("收藏成功");
               } else {
                   rs.setStatus("0003");
                   rs.setMessage("收藏失败");
               }
           }

           return rs.toJSONString();
       } catch (Exception e) {
           Response rs = new Response();
           rs.setStatus("0001");
           rs.setMessage("系统异常");
           return rs.toJSONString();
       }

    }

    /**
     * 删除收藏
     *
     * @param userId  用户ID
     * @param type    1-展商收藏；2-产品收藏
     * @param thirdId 展商id(产品id)
     * @return
     */
    @POST
    @Path(value = "/delUserCollection")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "删除收藏接口", notes = "可以通过该接口取消收藏的展商(产品)。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "取消收藏成功", response = Response.class),
            @ApiResponse(code = "0003", message = "取消收藏失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String delUserCollection(@ApiParam(value = "客户ID", required = true) @FormParam("userId") int userId,
                                    @ApiParam(value = "收藏类型，1-展商收藏；2-产品收藏", required = true) @FormParam("type") int type,
                                    @ApiParam(value = "展商id(产品id)", required = true) @FormParam("thirdId") int thirdId) {

        try {
            Response rs = new Response();
            int result = userCollectionService.delUserCollection(userId, type, thirdId);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("取消收藏成功");
            } else {
                rs.setStatus("0003");
                rs.setMessage("还未收藏，无法取消");
            }
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }


    /**
     * 查询收藏列表
     *
     * @param userId 用户ID
     * @param type   1-展商收藏；2-产品收藏
     * @param num    pagesize 分页用
     * @param lastId 记录起始位置，分页用
     * @return
     */
    @GET
    @Path(value = "/listUserCollection")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询收藏列表", notes = "可以进入“我的”-->“收藏夹”，通过该接口查询已收藏的展商（产品）。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = UserCollectionResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String listUserCollection(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId,
                                     @ApiParam(value = "类型，1-展商收藏；2-产品收藏", required = true) @QueryParam("type") int type,
                                     @ApiParam(value = "等同PageSize", required = true) @QueryParam("num") int num,
                                     @ApiParam(value = "最后一条记录ID，分页用", required = false) @QueryParam("lastId") int lastId) {
        try {
            UserCollectionResponse rs = new UserCollectionResponse();
            List<UserCollection> list = userCollectionService.listUserCollection(userId, type, num, lastId);
            if(null == list || list.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
                return rs.toJSONString();
            }

            List<UserCollectionResponse.UserCollectionVO> voList = new ArrayList<>();
            for(UserCollection collection : list){
                UserCollectionResponse.UserCollectionVO vo = rs.new UserCollectionVO();
                vo.setId(collection.getId());
                vo.setUserId(collection.getUserId());
                vo.setType(collection.getType());
                vo.setThirdId(collection.getThirdId());
                vo.setName(collection.getName());
                vo.setCreateTime(collection.getcTime());
                vo.setImageView(collection.getImageView());
                voList.add(vo);
            }

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(voList);
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }

    /**
     * 我浏览的展商（产品）列表
     *
     * @return
     */
    @GET
    @Path(value = "/listUserBrowse")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询我的浏览列表", notes = "可以进入“我的”-->“浏览记录”，通过该接口查询已浏览的展商（产品）记录。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = UserBrowseResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String listUserBrowse(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId,
                                 @ApiParam(value = "浏览类型，1-展商；2-产品", required = true) @QueryParam("type") int type,
                                 @ApiParam(value = "最后一条记录ID，分页用", required = false) @QueryParam("lastId") int lastId,
                                 @ApiParam(value = "等同PageSize", required = true) @QueryParam("num") int num) {

        try {
            UserBrowseResponse rs = new UserBrowseResponse();
            List<UserBrowse> list = userBrowseService.listUserBrowse(userId, type, num, lastId);
            if(null == list || list.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
                return rs.toJSONString();
            }

            List<UserBrowseResponse.UserBrowseVO> voList = new ArrayList<>();
            for(UserBrowse browse : list){
                UserBrowseResponse.UserBrowseVO vo = rs.new UserBrowseVO();
                vo.setId(browse.getId());
                vo.setUserId(browse.getUserId());
                vo.setType(browse.getType());
                vo.setThirdId(browse.getThirdId());
                vo.setName(browse.getName());
                vo.setNum(browse.getNum());
                vo.setCreateTime(browse.getcTime());
                vo.setModifyTime(browse.getmTime());
                vo.setImageView(browse.getImageView());
                voList.add(vo);
            }

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(voList);
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }

    /**
     * 我的下载记录
     *
     * @return
     */
    @GET
    @Path(value = "/listDownloadRecord")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询下载记录", notes = "可以进入“我的”-->“下载记录”，通过该接口查询我的下载记录。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = UserDownloadResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String listDownloadRecord(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId,
                                     @ApiParam(value = "最后一条记录ID，分页用", required = false) @QueryParam("lastId") int lastId,
                                     @ApiParam(value = "等同PageSize", required = true) @QueryParam("num") int num) {

        try {
            UserDownloadResponse rs = new UserDownloadResponse();
            List<UserDownload> list = userDownloadService.listDownload(userId,num, lastId);
            if(null == list || list.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
                return rs.toJSONString();
            }

            List<UserDownloadResponse.UserDownloadVO> voList = new ArrayList<>();
            for(UserDownload download : list){
                UserDownloadResponse.UserDownloadVO vo = rs.new UserDownloadVO();
                vo.setId(download.getId());
                vo.setUserId(download.getUserId());
                vo.setProductId(download.getProductId());
                vo.setProductName(download.getProductName());
                vo.setPdfUrl(download.getPdfUrl());
                vo.setCreateTime(download.getcTime());
                vo.setImageView(download.getImageView());
                voList.add(vo);
            }

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(voList);
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }



    /**
     * 我的下载记录
     *
     * @return
     */
    @GET
    @Path(value = "/addDownload")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "添加下载记录", notes = "下载pdf的时候调用", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String addDownload(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId,
                                     @ApiParam(value = "产品id", required = true) @QueryParam("productId") int productId) {

        try {
            Response rs = new Response();
            if(userId > 0){
                userDownloadService.addUserDownload(userId, productId);
            }


            rs.setStatus("0000");
            rs.setMessage("成功");
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }



    /**
     * 发送到邮箱
     *
     * @return
     */
    @GET
    @Path(value = "/sendMail")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "发送到邮箱", notes = "可以进入“我的”-->“下载记录”，点击发送到邮箱。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),
            @ApiResponse(code = "0002", message = "未设置邮箱", response = Response.class)
    })
    public String sendMail(@ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId,
                           @ApiParam(value = "需要发送邮件的id 多个使用逗号分隔", required = true) @QueryParam("ids") String ids) {
        Response rs = new Response();
        try {
            FUser fUser = fUserService.getFuser(userId);
            if(fUser.getEmail()==null || "".equals(fUser.getEmail())){
                rs.setStatus("0002");
                rs.setMessage("请先设置邮箱");
            }else{
                //TODO 将pdf文件发送到邮箱
            }
        } catch (Exception e) {
            e.printStackTrace();

            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }
        return rs.toJSONString();
    }


    @GET
    @Path(value = "/getImgRand")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取验证码之前需要先获取图片验证码", notes = "获取验证码之前需要先获取图片验证码", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "获取成功", response = ImageRandVO.class),
            @ApiResponse(code = "0101", message = "请先登录", response = ImageRandVO.class),
            @ApiResponse(code = "0002", message = "系统错误", response = ImageRandVO.class)
    })
    public String getImgRand(@ApiParam(value = "用户id", required = true) @QueryParam("userId") int userId){
        ImageRandVO result = new ImageRandVO();
        if(userId < 0){
            result.setStatus("0101");
            result.setMessage("请先登录");
            return JSON.toJSONString(result);
        }

        String securityCode = SecurityCode.getSecurityCode();


        byte[] image = SecurityImage.getImageAsInputStream(securityCode.replaceAll("", " "));

        BASE64Encoder encoder = new BASE64Encoder();

        String base64 = encoder.encode(image);

        RedisPool redis = RedisUtil.getRedisPool("user");

        String key = String.format(IMAGE_KRY, userId);

        redis.set(String.format(IMAGE_KRY,userId),securityCode);

        redis.expire(key,300);

        result.setStatus("0000");

        result.setResult(base64);

        return JSON.toJSONString(result);
    }


    @GET
    @Path(value = "/getPhoneRand")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "获取成功", response = Response.class),
            @ApiResponse(code = "0101", message = "请先登录", response = Response.class),
            @ApiResponse(code = "0002", message = "图片验证码错误，请重新填写", response = Response.class),
            @ApiResponse(code = "0003", message = "短信发送失败", response = Response.class)
    })
    public String getPhoneRand(@ApiParam(value = "用户id", required = true) @QueryParam("userId") int userId,
                               @ApiParam(value = "图片验证码", required = true) @QueryParam("imgRand") String imgRand,
                               @ApiParam(value = "手机号码", required = true) @QueryParam("phone") String phone){
        Response result = new Response();
        if(userId < 0){
            result.setStatus("0101");
            result.setMessage("请先登录");
            return JSON.toJSONString(result);
        }



        RedisPool redis = RedisUtil.getRedisPool("user");

        String key = String.format(IMAGE_KRY, userId);

        String imgRand_s = redis.get(key);

        redis.del(key);

        if(imgRand==null || !imgRand.equals(imgRand_s)){
            result.setStatus("0002");
            result.setMessage("图片验证码错误，请重新填写");
            return JSON.toJSONString(result);
        }

        String securityCode = SecurityCode.getSimpleSecurityCode();

        String phoneKey = String.format(PHONE_KRY, phone);

        redis.set(phoneKey,securityCode);

        redis.expire(key,300);

        ISmsService smsService = new QingSmsServiceImpl();

        int res = smsService.sendRand(phone,securityCode);

        if(res<=0){
            result.setStatus("0003");
            result.setMessage("短信发送失败");
            return JSON.toJSONString(result);
        }
        result.setStatus("0000");

        return JSON.toJSONString(result);
    }
}
