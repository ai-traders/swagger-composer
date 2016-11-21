package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;

import java.util.*;

public class MergedSwagger {
    private Swagger merged;
    private SwaggerElementsSet added;
    private List<SwaggerItem> ignored;
    private List<ConflictItem> conflicts;

    public MergedSwagger(Swagger merged, SwaggerElementsSet added, List<SwaggerItem> ignored, List<ConflictItem> conflicts) {
        this.merged = merged;
        this.added = added;
        this.ignored = ignored;
        this.conflicts = conflicts;
    }

    public boolean success() { return  conflicts.size() == 0; }

    public Swagger getMerged() {
        return merged;
    }

    public SwaggerElementsSet getAdded() {
        return added;
    }

    public List<SwaggerItem> getIgnored() {
        return ignored;
    }

    public List<ConflictItem> getConflicts() {
        return conflicts;
    }

    public Map<String, List<ConflictItem>> getConflictsByPairs() {
        Map<String, List<ConflictItem>> arrayMap = new HashMap<>();
        for(ConflictItem item : conflicts) {
            String bothLocations = item.getLeft().getSource() + " | " + item.getRight().getSource();
            if(!arrayMap.containsKey(bothLocations))
                arrayMap.put(bothLocations, new ArrayList<ConflictItem>());
            arrayMap.get(bothLocations).add(item);
        }
        return arrayMap;
    }
}
