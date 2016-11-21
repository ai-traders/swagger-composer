package com.ai_traders.swagger.composer;

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
}
