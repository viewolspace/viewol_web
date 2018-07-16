package com.viewol.product.action;

import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path(value = "product")
@Controller("product")
public class ProductAtion {
    @GET
    @Path(value = "/productList")
    @Produces("text/html;charset=UTF-8")
    public String productList() {

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
