package com.viewol.company.action;

import com.viewol.service.ICompanyService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public String recommentCompanyList() {
        companyService.queryRecommentCompany();

        return YouguuJsonHelper.returnJSON("0000", "ok");
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
    public String listCompany(@QueryParam("keyWord") String keyWord,
                              @QueryParam("categoryId") String categoryId,
                              @QueryParam("lastSeq") long lastSeq,
                              @QueryParam("num") int num) {
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
    public String getCompany(@QueryParam("id") int id) {
        companyService.getCompany(id);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

}
