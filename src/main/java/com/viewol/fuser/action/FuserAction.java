package com.viewol.fuser.action;

import com.viewol.pojo.FUser;
import com.viewol.service.ICompanyService;
import com.viewol.service.IFUserService;
import com.viewol.service.IProductService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;

@Path(value = "fuser")
@Controller("fuserAction")
public class FuserAction {

    @Resource
    private IFUserService fUserService;
    @Resource
    private IProductService productService;
    @Resource
    private ICompanyService companyService;

    /**
     * 查询我的基本信息
     *
     * @param userId
     * @return
     */
    @GET
    @Path(value = "/getFuser")
    @Produces("text/html;charset=UTF-8")
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
     * @param userId    用户ID
     * @param phone     手机号
     * @param company   公司名称
     * @param companyId 公司ID
     * @param position  职位
     * @param email     邮箱
     * @param age       年龄
     * @param questionOne 问题1答案
     * @param questionTwo 问题2答案
     * @param questionThree 问题3答案
     * @return
     */
    @POST
    @Path(value = "/addFUser")
    @Produces("text/html;charset=UTF-8")
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
     * 我收藏的产品列表
     * @return
     */
    @GET
    @Path(value = "/listCollectProduct")
    @Produces("text/html;charset=UTF-8")
    public String listCollectProduct(@QueryParam("companyId") int companyId,
                              @QueryParam("name") String name,
                              @QueryParam("categoryId") String categoryId,
                              @QueryParam("lastSeq") long lastSeq,
                              @QueryParam("num") int num) {
        productService.listProduct(companyId, name, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 我收藏的展商列表
     * @return
     */
    @GET
    @Path(value = "/listCollectCompany")
    @Produces("text/html;charset=UTF-8")
    public String listCollectCompany(@QueryParam("companyId") int companyId,
                                     @QueryParam("name") String name,
                                     @QueryParam("categoryId") String categoryId,
                                     @QueryParam("lastSeq") long lastSeq,
                                     @QueryParam("num") int num) {
//        companyService.listCompany()

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 我浏览的产品列表
     * @return
     */
    @GET
    @Path(value = "/listTraceProduct")
    @Produces("text/html;charset=UTF-8")
    public String listTraceProduct(@QueryParam("companyId") int companyId,
                                     @QueryParam("name") String name,
                                     @QueryParam("categoryId") String categoryId,
                                     @QueryParam("lastSeq") long lastSeq,
                                     @QueryParam("num") int num) {
        productService.listProduct(companyId, name, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 我浏览的展商列表
     * @return
     */
    @GET
    @Path(value = "/listTraceCompany")
    @Produces("text/html;charset=UTF-8")
    public String listTraceCompany(@QueryParam("companyId") int companyId,
                                     @QueryParam("name") String name,
                                     @QueryParam("categoryId") String categoryId,
                                     @QueryParam("lastSeq") long lastSeq,
                                     @QueryParam("num") int num) {
//        companyService.listCompany()

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 我的下载记录
     * @return
     */
    @GET
    @Path(value = "/listDownloadRecord")
    @Produces("text/html;charset=UTF-8")
    public String listDownloadRecord(@QueryParam("companyId") int companyId,
                                   @QueryParam("name") String name,
                                   @QueryParam("categoryId") String categoryId,
                                   @QueryParam("lastSeq") long lastSeq,
                                   @QueryParam("num") int num) {
//        companyService.listCompany()

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
