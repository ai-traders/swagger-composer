package com.ai_traders.swagger.composer;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Composer {
    public MergedSwagger merge(InputSwaggers swaggers) {
        Swagger target = swaggers.getMaster().getSwagger();
        SwaggerElementsSet toAdd = new SwaggerElementsSet();
        List<SwaggerItem> ignored = new ArrayList<>();
        List<ConflictItem> conflicts = new ArrayList<>();
        for(SwaggerSource partialSource : swaggers.getPartials()) {
            String origin = partialSource.getOrigin();
            Swagger partial = partialSource.getSwagger();
            Map<String, Model> partialDefs = partial.getDefinitions();
            if(partialDefs != null) {
                for (Map.Entry<String, Model> def : partialDefs.entrySet()) {
                    String key = def.getKey();
                    SwaggerItem<Model> newItem = new SwaggerItem<>(origin, key, def.getValue());
                    if (target.getDefinitions() != null && target.getDefinitions().containsKey(key)) {
                        ignored.add(newItem);
                    } else if (toAdd.getDefinitions().containsKey(key)) {
                        SwaggerItem<Model> otherdef = toAdd.getDefinitions().get(key);
                        if(!otherdef.getItem().equals(def.getValue()))
                           conflicts.add(new ConflictItem<Model>(newItem,otherdef));
                    } else {
                        // not in target, not in other partials
                        toAdd.getDefinitions().put(key, newItem);
                    }
                }
            }
            Map<String, Response> partialResponses = partial.getResponses();
            if(partialResponses != null) {
                for (Map.Entry<String, Response> response : partialResponses.entrySet()) {
                    String key = response.getKey();
                    SwaggerItem<Response> newItem = new SwaggerItem<>(origin, key, response.getValue());
                    if (target.getResponses() != null && target.getResponses().containsKey(key)) {
                        ignored.add(newItem);
                    } else if (toAdd.getResponses().containsKey(key)) {
                        // must be equal
                        SwaggerItem<Response> otherresponse = toAdd.getResponses().get(key);
                        if(!otherresponse.equals(response.getValue()))
                            conflicts.add(new ConflictItem<Response>(newItem,otherresponse));
                    } else {
                        // not in target, not in other partials
                        toAdd.getResponses().put(key, newItem);
                    }
                }
            }
            Map<String, Parameter> partialParams = partial.getParameters();
            if(partialParams != null) {
                for (Map.Entry<String, Parameter> parameter : partialParams.entrySet()) {
                    String key = parameter.getKey();
                    SwaggerItem<Parameter> newItem = new SwaggerItem<Parameter>(origin, key, parameter.getValue());
                    if (target.getParameters() != null && target.getParameter(key) != null) {
                        ignored.add(newItem);
                    } else if (toAdd.getParameters().containsKey(key)) {
                        SwaggerItem<Parameter> otherparam = toAdd.getParameters().get(key);
                        if(!otherparam.equals(parameter.getValue()))
                            conflicts.add(new ConflictItem<Parameter>(newItem,otherparam));
                    } else {
                        // not in target, not in other partials
                        toAdd.getParameters().put(key, newItem);
                    }
                }
            }

            String basePath = partial.getBasePath();
            for(Map.Entry<String, Path> path : partial.getPaths().entrySet()) {
                String key = path.getKey();
                SwaggerItem<Path> newItem = new SwaggerItem<>(origin, key, path.getValue());
                if(target.getPath(key) != null) {
                    ignored.add(newItem);
                }
                else if(toAdd.getPaths().containsKey(key)) {
                    SwaggerItem<Path> otherpath = toAdd.getPaths().get(key);
                    if(!otherpath.equals(path.getValue()))
                        conflicts.add(new ConflictItem<Path>(newItem,otherpath));
                }
                else {
                    if(basePath != null)
                        key = basePath + key;
                    toAdd.getPaths().put(key, newItem);
                }
            }
        }
        for(Map.Entry<String, SwaggerItem<Path>> path : toAdd.getPaths().entrySet()) {
            target.path(path.getKey(),path.getValue().getItem());
        }
        for(Map.Entry<String, SwaggerItem<Model>> def : toAdd.getDefinitions().entrySet()) {
            target.addDefinition(def.getKey(),def.getValue().getItem());
        }
        for(Map.Entry<String, SwaggerItem<Response>> response : toAdd.getResponses().entrySet()) {
            target.response(response.getKey(),response.getValue().getItem());
        }
        for(Map.Entry<String, SwaggerItem<Parameter>> parameter : toAdd.getParameters().entrySet()) {
            target.addParameter(parameter.getKey(),parameter.getValue().getItem());
        }
        return new MergedSwagger(target,toAdd,ignored,conflicts);
    }
}
