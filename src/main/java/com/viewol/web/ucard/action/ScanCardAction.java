package com.viewol.web.ucard.action;

import io.swagger.annotations.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by lenovo on 2018/8/1.
 */
@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="扫描名片")
        }
)
@Api(value = "scanCardAction")
@Path(value = "scanCard")
@Controller("scanCardAction")
public class ScanCardAction {
    @POST
    @Path(value = "/scanCard")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "扫描身份证信息", notes = "扫描身份证信息",
            author = "更新于 2018-07-16"  )
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })

    public String scanCard(  MultipartFormDataInput input


                          ){
        try {
            String content = input.getFormDataPart("content", String.class,null);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"status\":\"ok\"}";
    }



}
