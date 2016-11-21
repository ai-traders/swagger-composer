package com.ai_traders.swagger.composer;

public class SwaggerItem<T> {
    private String source;
    private T item;

    public SwaggerItem(String source, String key, T item) {
        this.source = source;
        this.item = item;
    }

    public String getSource() {
        return source;
    }

    public T getItem() {
        return item;
    }
}
