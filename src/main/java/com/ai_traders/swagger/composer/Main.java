package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Swagger swagger = new SwaggerParser().read("http://petstore.swagger.io/v2/swagger.json");
    }
}
