package com.viewol.company.action;

import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path(value = "/company")
@Controller("company")
public class CompanyAtion {

    @GET
    @Path(value = "/companyList")
    @Produces("text/html;charset=UTF-8")
    public String companyList() {

        return YouguuJsonHelper.returnJSON("0000", "ok");
    }
}
