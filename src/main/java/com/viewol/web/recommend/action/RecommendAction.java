package com.viewol.web.recommend.action;

import com.viewol.pojo.Recommend;
import com.viewol.service.IRecommendService;
import com.viewol.web.recommend.vo.RecommendModuleVO;
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
                @Tag(name="v1.0",description="同类推荐")
        }
)
@Api(value = "RecommendAction")
@Path(value = "recommend")
@Controller("recommendAction")
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
    @ApiOperation(value = "同类推荐查询", notes = "查询展商(产品)的同类推荐列表，展商（产品）主页最下面一排显示3个，可自动横向滚动，一共6个。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功" ,response = RecommendModuleVO.class)

    })
    public String listByCategory(@ApiParam(value = "分类ID", defaultValue = "00010001", required = true) @QueryParam("categoryId") String categoryId,
                                 @ApiParam(value = "1 展商 2 产品", defaultValue = "1", required = true) @QueryParam("type") int type) {

        if(categoryId!=null && categoryId.length()>8){ //只做第一层的推荐
            categoryId = categoryId.substring(0,8);
        }

        List<Recommend> list = recommendService.listByCategory(categoryId, type);

        return YouguuJsonHelper.returnJSON("0000", "ok",list);
    }
}
