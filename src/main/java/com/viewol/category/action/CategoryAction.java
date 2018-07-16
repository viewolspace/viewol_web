package com.viewol.category.action;

import com.viewol.service.ICategoryService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public String listCategory(@QueryParam("parentId") String parentId) {
        categoryService.listAll(parentId);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

}
