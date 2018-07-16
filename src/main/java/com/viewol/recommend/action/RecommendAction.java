package com.viewol.recommend.action;

import com.viewol.service.IRecommendService;
import com.youguu.core.util.json.YouguuJsonHelper;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

public class RecommendAction {

    @Resource
    private IRecommendService recommendService;

    /**
     * 同类推荐查询
     * @param categoryId 类别ID
     * @param type 1-展商；2-产品
     * @return
     */
    @GET
    @Path(value = "/listByCategory")
    @Produces("text/html;charset=UTF-8")
    public String listByCategory(@QueryParam("categoryId") String categoryId,
                                 @QueryParam("type") int type) {
        recommendService.listByCategory(categoryId, type);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
