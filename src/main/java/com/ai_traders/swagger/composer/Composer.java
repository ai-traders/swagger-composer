package com.ai_traders.swagger.composer;

import io.swagger.models.Path;
import io.swagger.models.Swagger;

import java.util.HashMap;
import java.util.Map;

public class Composer {
    public Swagger merge(InputSwaggers swaggers){
        //String basePath = swaggers.getPartials().get(0).getBasePath();
        Swagger target = swaggers.getMaster();
        // paths to be added
        Map<String,Path> paths = new HashMap<>();
        for(Swagger partial : swaggers.getPartials()) {
            for(Map.Entry<String, Path> path : partial.getPaths().entrySet()) {
                if(target.getPath(path.getKey()) != null) {
                    //TODO log ignore because in master
                }
                else if(paths.containsKey(path.getKey())) {
                    // must exist in target or be equal
                    throw new RuntimeException("not impl");
                }
                else {
                    paths.put(path.getKey(),path.getValue());
                }
            }
        }
        for(Map.Entry<String, Path> path : paths.entrySet()) {
            target.path(path.getKey(),path.getValue());
        }
        return target;
    }
}
