package com.viewol.web.category.action;

import com.viewol.pojo.Category;
import com.viewol.service.ICategoryService;
import com.viewol.web.category.vo.CategoryModuleVO;
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
        info = @Info(description ="为安防小程序，H5提供接口支持。",
                version = "v1.0.0",
                title = "安防小程序API文档",
                contact= @Contact(name="test",email="test@163.com")
        ),
        tags = {
                @Tag(name="v1.0",description="分类查询")
        }
)
@Api(value = "CategoryAction")
@Path(value = "category")
@Controller("categoryAction")
public class CategoryAction {

    @Resource
    private ICategoryService categoryService;

    /**
     * 查询展商(产品)分类
     * @param parentId 展商-0001，产品-0002
     * @return
     */
    @GET
    @Path(value = "/listCategory")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询展商(产品)分类", notes = "查询展商(产品)分类，展商只有一级分类 ， 产品多级分类一次性返回。需要客户端自己重新组织分类排序", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功" , response = CategoryModuleVO.class)

    })
    public String listCategory(@ApiParam(value = "0001 展商分类 0002 产品分类", defaultValue = "0001", required = true)@QueryParam("parentId") String parentId) {
        List<Category> list =  categoryService.listAll(parentId);

        return YouguuJsonHelper.returnJSON("0000", "ok" , list);
    }

}
