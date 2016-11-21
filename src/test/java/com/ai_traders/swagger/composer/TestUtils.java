package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static InputStreamReader createReader(String path) throws IOException {
        final InputStream resourceAsStream = getResourceAsStream(path);
        return new InputStreamReader(resourceAsStream);
    }

    public  static SwaggerSource loadSwaggerSource(String path) throws IOException {
        Swagger sw = new SwaggerParser().parse(loadString(path));
        return new SwaggerSource(sw,path);
    }

    public static String loadString(String path) throws IOException {
        final InputStream resourceAsStream = getResourceAsStream(path);
        return IOUtils.toString(resourceAsStream);
    }

    public static InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? TestUtils.class.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
