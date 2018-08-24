package com.viewol.web.info.action;

import com.viewol.pojo.Info;
import com.viewol.service.IInfoService;
import com.viewol.web.info.vo.InfoResponse;
import com.youguu.core.util.PropertiesUtil;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="资讯")
        }
)
@Api(value = "InfoAction")
@Path(value = "info")
@Controller("infoAction")
public class InfoAction {

    @Resource
    private IInfoService infoService;

    @GET
    @Path(value = "/list")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询资讯列表", notes = "", author = "更新于 2018-08-20")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功" ,response = InfoResponse.class),

    })
    public String infoList(@ApiParam(value = "最后一条记录ID", defaultValue = "0", required = true) @QueryParam("lastSeq") int lastSeq,
                           @ApiParam(value = "每页多少条记录", defaultValue = "10", required = true) @QueryParam("pageSize") int pageSize) {
        InfoResponse rs = new InfoResponse();
        try{
            List<Info> list =  infoService.listInfo(lastSeq, pageSize);
            if(list!=null && list.size()>0){
                rs.setStatus("0000");
                rs.setMessage("ok");
                List<InfoResponse.InfoVO> result = new ArrayList<>();
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

                Properties properties = PropertiesUtil.getProperties("properties/config.properties");
                String prefix = properties.getProperty("info.visit.url");
                for(Info info : list){
                    InfoResponse.InfoVO infoVO = rs.new InfoVO();
                    infoVO.setId(info.getId());
                    infoVO.setTitle(info.getTitle());
                    infoVO.setSummary(info.getSummary());
                    infoVO.setPubTime(dft.format(info.getPubTime()));
                    infoVO.setPicUrl(info.getPicUrl());
                    infoVO.setContentUrl(prefix + File.separator + info.getContentUrl());

                    result.add(infoVO);
                }
                rs.setResult(result);
            } else {
                rs.setStatus("0000");
                rs.setMessage("暂无数据");
            }
        } catch (Exception e){
            rs.setStatus("0001");
            rs.setMessage("系统异常");
        }


        return rs.toJSONString();

    }
}
