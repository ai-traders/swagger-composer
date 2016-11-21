package com.ai_traders.swagger.composer;

public class SwaggerItem<T> {
    private String source;
    private String key;
    private T item;

    public SwaggerItem(String source, String key, T item) {
        this.source = source;
        this.key = key;
        this.item = item;
    }

    public String getSource() {
        return source;
    }

    public T getItem() {
        return item;
    }

    public String getKey() {
        return key;
    }
}
