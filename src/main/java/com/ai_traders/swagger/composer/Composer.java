package com.ai_traders.swagger.composer;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;

import java.util.HashMap;
import java.util.Map;

public class Composer {
    public Swagger merge(InputSwaggers swaggers) {
        Swagger target = swaggers.getMaster();
        // definitions to be added
        Map<String, Model> definitions = new HashMap<>();
        Map<String, Response> responses = new HashMap<>();
        Map<String, Parameter> parameters = new HashMap<>();
        // paths to be added
        Map<String,Path> paths = new HashMap<>();
        for(Swagger partial : swaggers.getPartials()) {
            Map<String, Model> partialDefs = partial.getDefinitions();
            if(partialDefs != null) {
                for (Map.Entry<String, Model> def : partialDefs.entrySet()) {
                    if (target.getDefinitions() != null && target.getDefinitions().containsKey(def.getKey())) {
                        // TODO log ignore definition because exists in master
                    } else if (definitions.containsKey(def.getKey())) {
                        // must be equal
                        throw new RuntimeException("not impl");
                    } else {
                        // not in target, not in other partials
                        definitions.put(def.getKey(), def.getValue());
                    }
                }
            }
            Map<String, Response> partialResponses = partial.getResponses();
            if(partialResponses != null) {
                for (Map.Entry<String, Response> response : partialResponses.entrySet()) {
                    if (target.getResponses() != null && target.getResponses().containsKey(response.getKey())) {
                        // TODO log ignore response because exists in master
                    } else if (responses.containsKey(response.getKey())) {
                        // must be equal
                        throw new RuntimeException("not impl");
                    } else {
                        // not in target, not in other partials
                        responses.put(response.getKey(), response.getValue());
                    }
                }
            }
            Map<String, Parameter> partialParams = partial.getParameters();
            if(partialParams != null) {
                for (Map.Entry<String, Parameter> parameter : partialParams.entrySet()) {
                    if (target.getParameters() != null && target.getParameter(parameter.getKey()) != null) {
                        // TODO log ignore parameter because exists in master
                    } else if (parameters.containsKey(parameter.getKey())) {
                        // must be equal
                        throw new RuntimeException("not impl");
                    } else {
                        // not in target, not in other partials
                        parameters.put(parameter.getKey(), parameter.getValue());
                    }
                }
            }

            String basePath = partial.getBasePath();
            for(Map.Entry<String, Path> path : partial.getPaths().entrySet()) {
                if(target.getPath(path.getKey()) != null) {
                    //TODO log ignore because in master
                }
                else if(paths.containsKey(path.getKey())) {
                    // must be equal
                    throw new RuntimeException("not impl");
                }
                else {
                    String key = path.getKey();
                    if(basePath != null)
                        key = basePath + key;
                    paths.put(key,path.getValue());
                }
            }
        }
        for(Map.Entry<String, Path> path : paths.entrySet()) {
            target.path(path.getKey(),path.getValue());
        }
        for(Map.Entry<String, Model> def : definitions.entrySet()) {
            target.addDefinition(def.getKey(),def.getValue());
        }
        for(Map.Entry<String, Response> response : responses.entrySet()) {
            target.response(response.getKey(),response.getValue());
        }
        for(Map.Entry<String, Parameter> parameter : parameters.entrySet()) {
            target.addParameter(parameter.getKey(),parameter.getValue());
        }
        return target;
    }
}
