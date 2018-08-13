package com.viewol.web.product.action;

import com.alibaba.fastjson.JSONObject;
import com.viewol.pojo.Company;
import com.viewol.pojo.Product;
import com.viewol.pojo.UserBrowse;
import com.viewol.pojo.UserCollection;
import com.viewol.service.ICompanyService;
import com.viewol.service.IProductService;
import com.viewol.service.IUserBrowseService;
import com.viewol.service.IUserCollectionService;
import com.viewol.web.product.vo.ProductModuleVO;
import com.viewol.web.product.vo.ProductRootVO;
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
                @Tag(name="v1.0",description="产品")
        }
)
@Api(value = "ProductAtion")
@Path(value = "product")
@Controller("productAtion")
public class ProductAtion {

    @Resource
    private IProductService productService;

    @Resource
    private ICompanyService companyService;

    @Resource
    private IUserCollectionService userCollectionService;

    @Resource
    private IUserBrowseService userBrowseService;

    /**
     * 推荐产品查询，共12个
     *
     * @return
     */
    @GET
    @Path(value = "/recommentProductList")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询首页推荐产品列表", notes = "首页产品 然后自动横向滚动，一共12个。 <br/> 此接口需要使用的属性：id ，reImgView ，name", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功" ,response = ProductModuleVO.class),

    })
    public String recommentProductList() {

        List<Product> list =  productService.queryRecommentProduct();

        return YouguuJsonHelper.returnJSON("0000", "ok",list);

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
    @ApiOperation(value = "查询产品列表接口", notes = "1.产品Tab页查询产品列表调用该接口，支持按展商、产品名称，产品类别查询。<br/> 2.展商主页包含两个Tab页，第二个Tab显示展商的产品列表，调用此接口。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"  ,response = ProductModuleVO.class)

    })
    public String listProduct(@ApiParam(value = "展商的id ， 选填", defaultValue = "-1", required = false) @QueryParam("companyId") int companyId,
                              @ApiParam(value = "产品名称 ， 选填", defaultValue = "-1", required = false)         @QueryParam("name") String name,
                              @ApiParam(value = "分类id ， 选填", defaultValue = "-1", required = false)         @QueryParam("categoryId") String categoryId,
                              @ApiParam(value = "返回list最小的seq ， 第一页不需要传", defaultValue = "-1", required = false)         @QueryParam("lastSeq") long lastSeq,
                              @ApiParam(value = "数量 ， 必填", defaultValue = "5", required = true)         @QueryParam("num") int num) {
        List<Product> list = productService.listProduct(companyId, name, categoryId, lastSeq, num);

        return YouguuJsonHelper.returnJSON("0000", "ok",list);
    }

    /**
     * 查询产品基本信息
     * @param id
     * @return
     */
    @GET
    @Path(value = "/getProduct")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询产品基本信息", notes = "单个产品详情主页调用此接口。<br/> 注意内容 图片 下载地址使用 ***view字段", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功" ,response = ProductRootVO.class),

    })
    public String getProduct(@ApiParam(value = "产品id， 必填", defaultValue = "1", required = true)  @QueryParam("id") int id,
                             @ApiParam(value = "用户id 没有传0", defaultValue = "0", required = false) @QueryParam("userId") int userId) {

        Product product = productService.getProduct(id);

        int collection = 0;

        if(userId > 0){
            collection = userCollectionService.isCollection(userId, UserCollection.TYPE_COM,id);
        }

        JSONObject json = new JSONObject();
        json.put("code","0000");
        json.put("message","ok");
        json.put("result",product);
        json.put("collection",collection);

        if(product!=null){
            Company c =  companyService.getCompany(product.getCompanyId());
            json.put("company",c);
        }

        try{
            if(userId>0){
                userBrowseService.addUserBrowse(userId, UserBrowse.TYPE_PRODUCT,id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return json.toJSONString();
    }
}
