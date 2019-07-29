package com.viewol.web.product.action;

import com.alibaba.fastjson.JSONObject;
import com.viewol.pojo.Company;
import com.viewol.pojo.Product;
import com.viewol.pojo.UserCollection;
import com.viewol.pojo.UserInteract;
import com.viewol.service.*;
import com.viewol.util.Base64Img;
import com.viewol.web.common.Response;
import com.viewol.web.company.vo.ErCodeResponse;
import com.viewol.web.product.vo.ProductModuleVO;
import com.viewol.web.product.vo.ProductRootVO;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.io.File;
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
    @Resource
    private IWxService wxService;

    @Resource
    private IUserInteractService interactService;

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
    public String recommentProductList(@ApiParam(value = "展会编号", defaultValue = "1", required = true) @QueryParam("expoId" )  @DefaultValue("1") int expoId) {

        List<Product> list =  productService.queryRecommentProduct(expoId);

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
    public String listProduct(@ApiParam(value = "展会编号", defaultValue = "1", required = true) @QueryParam("expoId" )  @DefaultValue("1") int expoId,
                              @ApiParam(value = "展商的id ， 选填", defaultValue = "-1", required = false) @QueryParam("companyId") int companyId,
                              @ApiParam(value = "产品名称 ， 选填", defaultValue = "-1", required = false)         @QueryParam("name") String name,
                              @ApiParam(value = "分类id ， 选填", defaultValue = "-1", required = false)         @QueryParam("categoryId") String categoryId,
                              @ApiParam(value = "是否创新产品 0 不是  1是", defaultValue = "0", required = true) @QueryParam("award") @DefaultValue("0") int award,
                              @ApiParam(value = "返回list最小的seq ， 第一页不需要传", defaultValue = "-1", required = false)         @QueryParam("lastSeq") long lastSeq,
                              @ApiParam(value = "数量 ， 必填", defaultValue = "5", required = true)         @QueryParam("num") int num) {
        List<Product> list = productService.listProduct(expoId,companyId, name, categoryId, award , lastSeq, num);

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
        int isPraise = 0;

        if(userId > 0){
            collection = userCollectionService.isCollection(userId, UserCollection.TYPE_PRODUCT,id);
            UserInteract userInteract = interactService.getUserInteract(userId,id,UserInteract.CLASSIFY_PRODUCT,UserInteract.TYPE_PRAISE);
            if(userInteract!=null){
                isPraise = 1;
            }
        }

        JSONObject json = new JSONObject();
        json.put("code","0000");
        json.put("message","ok");
        json.put("result",product);
        json.put("collection",collection);
        json.put("isPraise",isPraise);
        if(product!=null){
            Company c =  companyService.getCompany(product.getCompanyId());
            json.put("company",c);
        }

        List<UserInteract> see = interactService.queryList(id, UserInteract.CLASSIFY_PRODUCT, UserInteract.TYPE_SEE, 12);

        List<UserInteract> praise = interactService.queryList(id, UserInteract.CLASSIFY_PRODUCT, UserInteract.TYPE_PRAISE, 12);

        List<UserInteract> comment = interactService.queryList(id, UserInteract.CLASSIFY_PRODUCT, UserInteract.TYPE_COMMENT, 12);

        json.put("see",see);
        json.put("praise",praise);
        json.put("comment",comment);

        try{
            if(userId>0){
                interactService.userInteract(userId, id, UserInteract.CLASSIFY_PRODUCT, UserInteract.TYPE_SEE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return json.toJSONString();
    }

    @POST
    @Path(value = "/getProductMaErCode")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "获取产品主页的小程序码", notes = "扫一扫该二维码跳转到产品的小程序主页，scene格式:a=1&c=123&p=43。a是type,c是展商ID,p是产品ID", author = "更新于 2018-08-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "成功", response = ErCodeResponse.class),
            @ApiResponse(code = "0002", message = "参数错误", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getProductMaErCode(@ApiParam(value = "哪个小程序 1 观展通  2 观展讯", required = true) @QueryParam("maNum") @DefaultValue("1") int maNum,
                                     @ApiParam(value = "type，1-代表交换名片") @FormParam("type") int type,
                                     @ApiParam(value = "产品ID", required = true) @FormParam("productId") int productId,
                                     @ApiParam(value = "展商ID", required = true) @FormParam("companyId") int companyId,
                                     @ApiParam(value = "二维码宽度，不填默认宽度430px") @FormParam("width") int width) {

        ErCodeResponse rs = new ErCodeResponse();

        try {
            File file = wxService.createProductWxaCode(maNum,type, companyId, productId, "pages/product/info", width);

            String base64Str = Base64Img.GetImageStrFromPath(file.getPath());
            if(file!=null){
                rs.setStatus("0000");
                rs.setMessage("成功");
                rs.setErcode(base64Str);
            } else {
                rs.setStatus("0001");
                rs.setMessage("系统异常");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            e.printStackTrace();
        }


        return rs.toJSONString();
    }


    @POST
    @Path(value = "/comment")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "产品评论", notes = "产品评论", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "评论成功", response = Response.class),
            @ApiResponse(code = "0002", message = "评论失败", response = Response.class)
    })
    public String updateUser(@ApiParam(value = "用户id", required = true) @FormParam("userId") int userId,
                             @ApiParam(value = "产品id", required = true) @FormParam("productId") int productId,
                             @ApiParam(value = "评论内容", required = true) @FormParam("content") String content) {
        try {
            Response rs = new Response();

            interactService.userComment(userId,UserInteract.CLASSIFY_PRODUCT,productId,content);

            rs.setStatus("0000");

            return rs.toJSONString();
        } catch (Exception e){
            e.printStackTrace();
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }



    @POST
    @Path(value = "/praise")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "产品点赞", notes = "产品点赞", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "评论成功", response = Response.class),
            @ApiResponse(code = "0002", message = "评论失败", response = Response.class)
    })
    public String praise(@ApiParam(value = "用户id", required = true) @FormParam("userId") int userId,
                             @ApiParam(value = "产品id", required = true) @FormParam("productId") int productId) {
        try {
            Response rs = new Response();

            interactService.userInteract(userId, productId,UserInteract.CLASSIFY_PRODUCT, UserInteract.TYPE_PRAISE);

            rs.setStatus("0000");

            return rs.toJSONString();
        } catch (Exception e){
            e.printStackTrace();
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }
}
