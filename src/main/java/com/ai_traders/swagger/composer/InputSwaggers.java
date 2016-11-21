package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputSwaggers {
    private Swagger master;
    private List<Swagger> partials;

    public InputSwaggers(Swagger master, Swagger... partials) {
        this.master = master;
        this.partials = Arrays.asList(partials);
    }

    public InputSwaggers(String master, String... partials) {
        SwaggerParser parser = new SwaggerParser();
        this.master = parser.parse(master);
        this.partials = new ArrayList<Swagger>();
        for(String part : partials) {
            this.partials.add(parser.parse(part));
        }
    }

    public Swagger getMaster() {
        return master;
    }

    public List<Swagger> getPartials() {
        return partials;
    }
}
