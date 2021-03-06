package com.viewol.web.company.action;

import com.alibaba.fastjson.JSONObject;
import com.viewol.pojo.*;
import com.viewol.service.*;
import com.viewol.util.Base64Img;
import com.viewol.web.common.Response;
import com.viewol.web.company.vo.CompanyListVO;
import com.viewol.web.company.vo.CompanyModuleVO;
import com.viewol.web.company.vo.ErCodeResponse;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.io.File;
import java.util.List;

@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="展商")
        }
)
@Api(value = "CompanyAtion")
@Path(value = "/company")
@Controller("companyAtion")
public class CompanyAtion {

    @Resource
    private ICompanyService companyService;
    @Resource
    private IUserCollectionService userCollectionService;
    @Resource
    private IUserBrowseService userBrowseService;
    @Resource
    private IWxService wxService;
    @Resource
    private IBUserService bUserService;
    /**
     * 推荐展商查询，共12个
     * @return
     */
    @GET
    @Path(value = "/recommentCompanyList")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询首页推荐展商列表", notes = "首页展商推荐一排3个，显示两排，然后自动横向滚动，一共12个。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功",response = CompanyListVO.class)

    })
    public String recommentCompanyList() {
        List<Company> list  =  companyService.queryRecommentCompany();

        return YouguuJsonHelper.returnJSON("0000", "ok",list);
    }

    /**
     * 查询展商搜索主页列表
     * @param keyWord
     * @param categoryId 展商类别
     * @param lastSeq 分页使用 start
     * @param num 每页多少条pagesize
     * @return
     */
    @GET
    @Path(value = "/listCompany")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "展商TAB页查询接口", notes = "查询展商列表，支持按关键词、类别搜索，上拉可以加载更多。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = CompanyListVO.class)

    })
    public String listCompany(@ApiParam(value = "搜索关键词 选填", defaultValue = "", required = false) @QueryParam("keyWord") String keyWord,
                              @ApiParam(value = "展商分类 选填", defaultValue = "", required = false) @QueryParam("categoryId") String categoryId,
                              @ApiParam(value = "最小seq 第一页不需要传", defaultValue = "", required = false) @QueryParam("lastSeq") long lastSeq,
                              @ApiParam(value = "数量 必填", defaultValue = "5", required = true) @QueryParam("num") int num) {
        List<Company> list  = companyService.listCompany(keyWord, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok",list);
    }

    /**
     * 查询展商基本信息
     * @param id
     * @return
     */
    @GET
    @Path(value = "/getCompany")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询展商基本信息", notes = "展商主页包含两个Tab页，第一Tab显示展商基本信息。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = CompanyModuleVO.class),

    })
    public String getCompany(@ApiParam(value = "展商的id", defaultValue = "", required = true) @QueryParam("id") int id,
                             @ApiParam(value = "用户id 没有传0", defaultValue = "0", required = false) @QueryParam("userId") int userId) {

        String categoryId = null;

        Company company = companyService.getCompany(id);

        List<CompanyCategory> list = companyService.queryCompanyCategory(id);

        if(list!=null && list.size()>0){
            categoryId = list.get(0).getCategoryId();
        }

        int collection = 0;

        if(userId > 0){
            collection = userCollectionService.isCollection(userId, UserCollection.TYPE_COM,id);
        }
        JSONObject json = new JSONObject();
        json.put("code","0000");
        json.put("message","ok");
        json.put("result",company);
        json.put("categoryId",categoryId);
        json.put("collection",collection);


        try{
            if(userId>0){
                userBrowseService.addUserBrowse(userId, UserBrowse.TYPE_COM,id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return json.toJSONString();
    }


    @POST
    @Path(value = "/getCompanyMaErCode")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取展商/业务员的小程序码", notes = "扫一扫该二维码跳转到展商的小程序主页，scene格式:a=1&c=123&u=12。a是type,c是展商ID,u是业务员ID", author = "更新于 2018-08-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "成功", response = ErCodeResponse.class),
            @ApiResponse(code = "0002", message = "参数错误", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getCompanyMaErCode(@ApiParam(value = "type，1-代表交换名片", required = true) @FormParam("type") int type,
                              @ApiParam(value = "业务员ID", required = true) @FormParam("bUserId") int bUserId,
                              @ApiParam(value = "展商ID", required = true) @FormParam("companyId") int companyId,
                              @ApiParam(value = "二维码宽度，不填默认宽度430px") @FormParam("width") int width) {

        ErCodeResponse rs = new ErCodeResponse();

        try{
            //没有的话 查询默认第一个业务员ID
            if(bUserId<=0){
                List<BUser> bUserList = bUserService.listByCom(companyId);
                if(bUserList!=null && bUserList.size()>0){
                    bUserId = bUserList.get(0).getUserId();
                }
            }
            File file = wxService.createCompanyWxaCode(type, companyId, bUserId, "pages/company/index", width);

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

    @POST
    @Path(value = "/getProgramMaErCode")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取小程序码", notes = "扫一扫该二维码跳转到小程序主页", author = "更新于 2018-08-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "成功", response = ErCodeResponse.class),
            @ApiResponse(code = "0002", message = "参数错误", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getProgramMaErCode(@ApiParam(value = "二维码宽度", required = true) @FormParam("width") int width) {

        ErCodeResponse rs = new ErCodeResponse();

        try{
            File file = wxService.createProgramWxaCode(width, "pages/index/index");

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
}
