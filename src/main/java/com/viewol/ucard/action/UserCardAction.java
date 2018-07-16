package com.viewol.ucard.action;

import com.viewol.pojo.query.UserCardQuery;
import com.viewol.service.IUserCardService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path( value = "ucard")
@Controller("userCardAction")
public class UserCardAction {

    @Resource
    private IUserCardService userCardService;

    /**
     * 查询展商名片夹列表
     * @return
     */
    @GET
    @Path(value = "/ucardList")
    @Produces("text/html;charset=UTF-8")
    public String ucardList(@QueryParam("companyId") int companyId,
                            @QueryParam("lastId") int lastId,
                            @QueryParam("pageSize") int pageSize) {
        UserCardQuery query = new UserCardQuery();
        query.setCompanyId(companyId);
        query.setLastId(lastId);
        query.setPageSize(pageSize);
        userCardService.listUserCard(query);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 查询我的名片夹列表
     * @return
     */
    @GET
    @Path(value = "/mycardList")
    @Produces("text/html;charset=UTF-8")
    public String mycardList(@QueryParam("fUserId") int fUserId,
                            @QueryParam("lastId") int lastId,
                            @QueryParam("pageSize") int pageSize) {
        UserCardQuery query = new UserCardQuery();
        query.setfUserId(fUserId);
        query.setLastId(lastId);
        query.setPageSize(pageSize);
        userCardService.listUserCard(query);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
