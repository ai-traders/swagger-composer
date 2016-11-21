package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputSwaggers {
    private SwaggerSource master;
    private List<SwaggerSource> partials;

    public InputSwaggers(SwaggerSource master, SwaggerSource... partials) {
        this.master = master;
        this.partials = Arrays.asList(partials);
    }

    public InputSwaggers(SwaggerSource master, List<SwaggerSource> partials) {
        this.master = master;
        this.partials = partials;
    }

    public SwaggerSource getMaster() {
        return master;
    }

    public List<SwaggerSource> getPartials() {
        return partials;
    }
}
