package com.viewol.fuser.action;

import com.viewol.pojo.FUser;
import com.viewol.service.*;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;

@SwaggerDefinition(
        tags = {
                @Tag(name = "v1.0", description = "个人信息")
        }
)
@Api(value = "FuserAction")
@Path(value = "fuser")
@Controller("fuserAction")
public class FuserAction {

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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String getFuser(@QueryParam("userId") int userId) {
        fUserService.getFuser(userId);

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String updateUser(@FormParam("userId") int userId,
                             @FormParam("phone") String phone,
                             @FormParam("company") String company,
                             @FormParam("companyId") int companyId,
                             @FormParam("position") String position,
                             @FormParam("email") String email,
                             @FormParam("age") int age) {
        FUser fUser = fUserService.getFuser(userId);

        fUserService.updateUser(fUser);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 个人信息录入+问卷调查
     *
     * @param userId        用户ID
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String addFUser(@FormParam("userId") int userId,
                           @FormParam("phone") String phone,
                           @FormParam("company") String company,
                           @FormParam("companyId") int companyId,
                           @FormParam("position") String position,
                           @FormParam("email") String email,
                           @FormParam("age") int age,
                           @FormParam("questionOne") String questionOne,
                           @FormParam("questionTwo") String questionTwo,
                           @FormParam("questionThree") String questionThree) {
        FUser fUser = new FUser();

//        fUserService.addFUser(fUser);

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String addUserCollection(@FormParam("userId") int userId,
                                    @FormParam("agtypee") int type,
                                    @FormParam("thirdId") int thirdId) {

        userCollectionService.addUserCollection(userId, type, thirdId);
        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String delUserCollection(@FormParam("userId") int userId,
                                    @FormParam("agtypee") int type,
                                    @FormParam("thirdId") int thirdId) {

        userCollectionService.delUserCollection(userId, type, thirdId);
        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String listUserCollection(@QueryParam("userId") int userId,
                                     @QueryParam("type") int type,
                                     @QueryParam("num") int num,
                                     @QueryParam("lastId") int lastId) {
        userCollectionService.listUserCollection(userId, type, num, lastId);

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String listUserBrowse(@QueryParam("userId") int userId,
                                 @QueryParam("type") int type,
                                 @QueryParam("lastId") int lastId,
                                 @QueryParam("num") int num) {
        userBrowseService.listUserBrowse(userId, type, num, lastId);

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String listDownloadRecord(@QueryParam("userId") int userId,
                                     @QueryParam("lastId") int lastId,
                                     @QueryParam("num") int num) {
        userDownloadService.listDownload(userId, lastId, num);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
