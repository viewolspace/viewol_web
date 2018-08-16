package com.viewol.web.ucard.action;

import com.alibaba.fastjson.JSONObject;
import com.viewol.cardscan.IScanCard;
import com.viewol.cardscan.ScanTestXY;
import io.swagger.annotations.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Controller;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String scanCard(  MultipartFormDataInput input ){
        InputStream is = null;
        ByteArrayOutputStream os = null;
        ByteArrayInputStream bis = null;
        JSONObject json = new JSONObject();
        try {

            List<InputPart> list=input.getFormDataMap().get("img");
            if(list!=null&&list.size()>0){
                InputPart part = list.get(0);
                is=part.getBody(InputStream.class,null);
                byte[] fileByte = IOUtils.toByteArray(is);

                bis= new ByteArrayInputStream(fileByte);
                bis.mark(0);
                BufferedImage bi = ImageIO.read(bis);

                int h = bi.getHeight();

                int w = bi.getWidth();

                os=new ByteArrayOutputStream();

                System.out.println("宽度：" + w + " 高度："+h);

                //

                bis.reset();

                if(w > h && w > 960){ //正常的横屏

                    Thumbnails.of(bis).size(960, 600).toOutputStream(os);

                    System.out.println(os.toByteArray().length);

                }else if( h > w && h > 960){
                    Thumbnails.of(bis).size(w/(h/960), 960).toOutputStream(os);

                    System.out.println(os.toByteArray().length);
                }

                BASE64Encoder encoder = new BASE64Encoder();
                String base64 = encoder.encode(os.toByteArray());
                Map<String,String> map = new HashMap<>();
                map.put("base64",base64);

//                IScanCard sc = new ScanTestHW();
//                System.out.println("hw:" + sc.scan(map));

                IScanCard sc1 = new ScanTestXY();
                json = sc1.scan(map);
//                System.out.println("xy:" + sc1.scan(map));


//                //判断图片大小
//                double img_size = fileByte.length/1024;
//                double scale = 1;
//                if(img_size > 150){
//                    scale = 150/img_size;
//                }
//
//
//                bis= new ByteArrayInputStream(fileByte);
//
//
//
//                Thumbnails.of(bis).scale(scale).toOutputStream(os);
//
//                System.out.println(os.toByteArray().length/1024);

            }

        } catch (Exception e) {
            json = new JSONObject();
            json.put("status","0001");
            e.printStackTrace();
        }finally {
            if(is!=null){ try {is.close();} catch (IOException e) {e.printStackTrace();}}
            if(os!=null){ try {os.close();} catch (IOException e) {e.printStackTrace();}}
            if(bis!=null){ try {bis.close();} catch (IOException e) {e.printStackTrace();}}
        }

        return json.toJSONString();
    }



}
