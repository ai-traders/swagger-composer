package com.ai_traders.swagger.composer;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

public class ConflictItem<T> {
    private SwaggerItem<T> left;
    private SwaggerItem<T> right;

    public ConflictItem(SwaggerItem<T> left, SwaggerItem<T> right) {
        this.left = left;
        this.right = right;
    }

    public SwaggerItem<T> getLeft() {
        return left;
    }

    public SwaggerItem<T> getRight() {
        return right;
    }

    public String getType() {
        if(left.getItem() instanceof Path)
            return "Path";
        if(left.getItem() instanceof Model)
            return "Definition";
        if(left.getItem() instanceof Response)
            return "Response";
        if(left.getItem() instanceof Parameter)
            return "Parameter";
        return "Item";
    }
}
