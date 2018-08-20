package com.viewol.web.ucard.action;

import com.viewol.pojo.BUser;
import com.viewol.pojo.UserCardVO;
import com.viewol.pojo.query.UserCardQuery;
import com.viewol.service.IBUserService;
import com.viewol.service.IUserCardService;
import com.viewol.web.common.Response;
import com.viewol.web.ucard.vo.UserCardResponse;
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

    @Resource
    private IBUserService userService;




    /**
     * 查询展商名片夹列表
     * @return
     */
    @GET
    @Path(value = "/ucardList")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询展商名片夹列表（H5）", notes = "展商管理员登录H5页面，查询自己的名片夹列表。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = UserCardResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String ucardList(@ApiParam(value = "展商ID", required = true) @QueryParam("companyId") int companyId,
                            @ApiParam(value = "业务员ID", required = true) @QueryParam("userId") int userId,
                            @ApiParam(value = "最后一条记录ID，分页用", required = true) @QueryParam("lastId") int lastId,
                            @ApiParam(value = "每次返回记录数", required = true) @QueryParam("pageSize") int pageSize) {
        try {
            UserCardQuery query = new UserCardQuery();
            query.setCompanyId(companyId);
            query.setLastId(lastId);
            query.setPageSize(pageSize);
            query.setbUserId(userId);
            List<UserCardVO> list = userCardService.listUserCard(query);

            UserCardResponse rs = new UserCardResponse();
            if(null == list || list.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
                return rs.toJSONString();
            }

            List<UserCardResponse.UserCardVO> voList = new ArrayList<>();
            for(UserCardVO userCardVO : list){
                UserCardResponse.UserCardVO vo = rs.new UserCardVO();
                vo.setId(userCardVO.getId());
                vo.setbUserId(userCardVO.getbUserId());
                vo.setCompanyId(userCardVO.getCompanyId());
                vo.setfUserId(userCardVO.getfUserId());
                vo.setCreateTime(userCardVO.getcTime());

                //客户基本信息
                vo.setfUserName(userCardVO.getfUserName());
                vo.setfPhone(userCardVO.getfPhone());
                vo.setFCompany(userCardVO.getFCompany());
                vo.setfPosition(userCardVO.getfPosition());
                vo.setfEmail(userCardVO.getfEmail());
                vo.setFAge(userCardVO.getFAge());
                vo.setFCompanyId(userCardVO.getFCompanyId());

                //展商业务员基本信息
                vo.setBUserName(userCardVO.getBUserName());
                vo.setBPhone(userCardVO.getBPhone());
                vo.setBPosition(userCardVO.getBPosition());

                //展商基本信息
                vo.setLogo(userCardVO.getLogo());
                vo.setName(userCardVO.getName());
                vo.setShortName(userCardVO.getShortName());

                voList.add(vo);
            }

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(voList);
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }

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
            @ApiResponse(code = "0000", message = "请求成功", response = UserCardResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String mycardList(@ApiParam(value = "客户ID", required = true) @QueryParam("fUserId") int fUserId,
                             @ApiParam(value = "最后一条记录ID，分页用", required = true) @QueryParam("lastId") int lastId,
                             @ApiParam(value = "每次返回记录数", required = true) @QueryParam("pageSize") int pageSize) {

        try {
            UserCardQuery query = new UserCardQuery();
            query.setfUserId(fUserId);
            query.setLastId(lastId);
            query.setPageSize(pageSize);
            List<UserCardVO> list = userCardService.listUserCard(query);

            UserCardResponse rs = new UserCardResponse();
            if(null == list || list.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
                return rs.toJSONString();
            }

            List<UserCardResponse.UserCardVO> voList = new ArrayList<>();
            for(UserCardVO userCardVO : list){
                UserCardResponse.UserCardVO vo = rs.new UserCardVO();
                vo.setId(userCardVO.getId());
                vo.setbUserId(userCardVO.getbUserId());
                vo.setCompanyId(userCardVO.getCompanyId());
                vo.setfUserId(userCardVO.getfUserId());
                vo.setCreateTime(userCardVO.getcTime());

                //客户基本信息
                vo.setfUserName(userCardVO.getfUserName());
                vo.setfPhone(userCardVO.getfPhone());
                vo.setFCompany(userCardVO.getFCompany());
                vo.setfPosition(userCardVO.getfPosition());
                vo.setfEmail(userCardVO.getfEmail());
                vo.setFAge(userCardVO.getFAge());
                vo.setFCompanyId(userCardVO.getFCompanyId());

                //展商业务员基本信息
                vo.setBUserName(userCardVO.getBUserName());
                vo.setBPhone(userCardVO.getBPhone());
                vo.setBPosition(userCardVO.getBPosition());

                //展商基本信息
                vo.setLogo(userCardVO.getLogoView());
                vo.setName(userCardVO.getName());
                vo.setShortName(userCardVO.getShortName());
                voList.add(vo);
            }

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(voList);
            return rs.toJSONString();
        } catch (Exception e) {
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
    }


    @GET
    @Path(value = "/changeCard")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "用户与展商互换名片", notes = "用户与展商互换名片，此功能是用户扫描展商二维码的时候实现的", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "交换成功", response = Response.class),
            @ApiResponse(code = "0001", message = "已经交换过名片", response = Response.class),
            @ApiResponse(code = "0002", message = "参数错误 ， userId为0 或者 bUserId为0", response = Response.class),
            @ApiResponse(code = "0003", message = "交换失败", response = Response.class)
    })
    public String changeCard(@ApiParam(value = "展商ID", required = true) @QueryParam("companyId") int companyId,
                            @ApiParam(value = "用户id", required = true) @QueryParam("userId") int userId,
                            @ApiParam(value = "业务员id ， 扫码传递", required = true) @QueryParam("bUserId") int bUserId) {
        if(userId <= 0){
            return YouguuJsonHelper.returnJSON("0002","参数错误");
        }

        if(bUserId<=0){//查询第一个业务员
            List<BUser> list = userService.listByCom(companyId);
            if(list==null || list.size()==0){
                return YouguuJsonHelper.returnJSON("0002","该展商目前没有业务员");
            }

            bUserId = list.get(0).getUserId();
        }

        UserCardQuery query = new UserCardQuery();
        query.setfUserId(userId);
        query.setCompanyId(companyId);

        List<UserCardVO> list = userCardService.listUserCard(query);

        if(list!=null && list.size()>0){
            return YouguuJsonHelper.returnJSON("0001","已经交换过名片");
        }else{
            int result = userCardService.addUserCard(userId, bUserId, companyId);

            if(result>0){
                return YouguuJsonHelper.returnJSON("0000","交换成功");
            }
        }
        return YouguuJsonHelper.returnJSON("0003","交换失败");
    }




}
