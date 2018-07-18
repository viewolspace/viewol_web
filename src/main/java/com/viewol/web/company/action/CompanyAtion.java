package com.viewol.web.company.action;

import com.alibaba.fastjson.JSONObject;
import com.viewol.pojo.Company;
import com.viewol.pojo.CompanyCategory;
import com.viewol.service.ICompanyService;
import com.viewol.web.company.vo.CompanyListVO;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        companyService.listCompany(keyWord, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String getCompany(@ApiParam(value = "展商的id", defaultValue = "", required = false) @QueryParam("id") int id) {

        String categoryId = null;

        Company company = companyService.getCompany(id);

        List<CompanyCategory> list = companyService.queryCompanyCategory(id);

        if(list!=null && list.size()>0){
            categoryId = list.get(0).getCategoryId();
        }


        JSONObject json = new JSONObject();
        json.put("code","0000");
        json.put("message","ok");
        json.put("result",company);
        json.put("categoryId",categoryId);


        return json.toJSONString();
    }

}
