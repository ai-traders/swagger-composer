package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwaggerSource {
    private Swagger swagger;
    private String origin;

    public SwaggerSource(Swagger swagger) {
        this.swagger = swagger;
        this.origin = "unknown";
    }
    public SwaggerSource(Swagger swagger,String origin) {
        this.swagger = swagger;
        this.origin = origin;
    }

    /**
     * Loads swagger definition from specified source.
     * @param location
     */
    public SwaggerSource(String location) {
        SwaggerParser parser = new SwaggerParser();
        this.swagger = parser.read(location);
        this.origin = location;
    }


    public Swagger getSwagger() {
        return swagger;
    }

    public String getOrigin() {
        return origin;
    }
}
