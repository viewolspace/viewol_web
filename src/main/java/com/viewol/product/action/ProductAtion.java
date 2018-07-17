package com.viewol.product.action;

import com.viewol.service.IProductService;
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
                @Tag(name="v1.0",description="产品")
        }
)
@Api(value = "ProductAtion")
@Path(value = "product")
@Controller("productAtion")
public class ProductAtion {

    @Resource
    private IProductService productService;

    /**
     * 推荐产品查询，共12个
     *
     * @return
     */
    @GET
    @Path(value = "/recommentProductList")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询首页推荐产品列表", notes = "首页产品推荐一排3个，显示两排，然后自动横向滚动，一共12个。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String recommentProductList() {
        productService.queryRecommentProduct();

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 1.产品搜索主页列表查询。2.展商主页查询某展商的产品列表。
     * @param companyId 展商ID
     * @param name 产品名称
     * @param categoryId 产品类别
     * @param lastSeq 分页使用 start
     * @param num 每页多少条pagesize
     * @return
     */
    @GET
    @Path(value = "/listProduct")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询产品列表接口", notes = "1.产品Tab页查询产品列表调用该接口，支持按展商、产品名称，产品类别查询。\r\n2.展商主页包含两个Tab页，第二个Tab显示展商的产品列表，调用此接口。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String listProduct(@QueryParam("companyId") int companyId,
                                       @QueryParam("name") String name,
                                       @QueryParam("categoryId") String categoryId,
                                       @QueryParam("lastSeq") long lastSeq,
                                       @QueryParam("num") int num) {
        productService.listProduct(companyId, name, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 查询产品基本信息
     * @param id
     * @return
     */
    @GET
    @Path(value = "/getProduct")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询产品基本信息", notes = "单个产品详情主页调用此接口。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String getProduct(@QueryParam("id") int id) {
        productService.getProduct(id);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
