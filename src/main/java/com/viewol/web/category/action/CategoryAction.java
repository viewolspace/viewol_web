package com.viewol.web.category.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.List;

@SwaggerDefinition(
        info = @Info(description ="为安防小程序，H5提供接口支持。" +
                "<br>0000:成功返回。" +
                "<br>0001:系统异常。" +
                "<br>0101:登录状态失效，重新登录。",
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

        JSONArray root = this.parseData(list);

        return YouguuJsonHelper.returnJSON("0000", "ok" , root);
    }







    private JSONArray parseData(List<Category> list){
        JSONArray root = new JSONArray();
        List<JSONObject> temp = new ArrayList<>();
        for(Category category:list){
            JSONObject json = new JSONObject();
            json = JSON.parseObject(JSON.toJSONString(category));
            temp.add(json);
        }

        for(JSONObject j1:temp){
            for(JSONObject j2:temp){
                if(j1.getString("id").equals(j2.getString("parentId"))){
                    JSONArray array = j1.getJSONArray("child");
                    if(array==null){
                        array=new JSONArray();
                        j1.put("child",array);
                    }
                    array.add(j2);
                }
            }
        }

        for(JSONObject j1:temp){
            if(j1.getString("parentId").length()==4){
                root.add(j1);
            }
        }


        return root;
    }

}
