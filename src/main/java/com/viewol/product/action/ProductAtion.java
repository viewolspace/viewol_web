package com.viewol.product.action;

import com.viewol.service.IProductService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public String getProduct(@QueryParam("id") int id) {
        productService.getProduct(id);

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
