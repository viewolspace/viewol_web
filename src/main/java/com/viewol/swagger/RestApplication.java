package com.viewol.swagger;

import com.youguu.core.util.PropertiesUtil;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RestApplication extends Application {

    public RestApplication() {
        Properties properties = null;
        try {
            properties = PropertiesUtil.getProperties("properties/swagger.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null == properties){
            System.out.println("加载properties/swagger.properties出错");
            return;
        }
        String version = properties.getProperty("version", "v1.0.0");
        String resourcePackage = properties.getProperty("resource.package", "com.viewol");
        String scan = properties.getProperty("scan", "true");
        String basePath = properties.getProperty("base.path");
        String host = properties.getProperty("host");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(version);
        beanConfig.setResourcePackage(resourcePackage);
        beanConfig.setScan("true".equals(scan));
        beanConfig.setBasePath(basePath);
        beanConfig.setHost(host);
    }


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return resources;
    }

}