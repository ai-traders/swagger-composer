package com.ai_traders.swagger.composer;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Mergeable elements of swagger spec
 */
public class SwaggerElementsSet {
    // definitions to be added
    private Map<String, SwaggerItem<Model>> definitions;
    private Map<String, SwaggerItem<Response>> responses;
    private Map<String, SwaggerItem<Parameter>> parameters;
    private Map<String, SwaggerItem<Path>> paths;

    public SwaggerElementsSet() {
        definitions = new HashMap<>();
        responses = new HashMap<>();
        parameters = new HashMap<>();
        paths = new HashMap<>();
    }

    public Map<String, SwaggerItem<Model>> getDefinitions() { return definitions; }
    public Map<String, SwaggerItem<Response>> getResponses() { return responses; }
    public Map<String, SwaggerItem<Parameter>> getParameters() { return parameters; }
    public Map<String, SwaggerItem<Path>> getPaths() { return paths; }

}
