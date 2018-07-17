package com.viewol.ucard.action;

import com.viewol.pojo.query.UserCardQuery;
import com.viewol.service.IUserCardService;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="名片夹")
        }
)
@Api(value = "UserCardAction")
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
    @ApiOperation(value = "查询展商名片夹列表（H5）", notes = "展商管理员登录H5页面，查询自己的名片夹列表。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
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
    @ApiOperation(value = "查询我的名片夹列表（小程序）", notes = "客户进入我的-->名片夹，查询自己的名片夹列表。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
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
