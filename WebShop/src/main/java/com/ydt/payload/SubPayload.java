package com.ydt.payload;

import com.ydt.entity.Category;

public class SubPayload {
    private Category object;

    public SubPayload() {
    }

    public SubPayload(Category object) {
        this.object = object;
    }

    public Category getObject() {
        return object;
    }

    public void setObject(Category object) {
        this.object = object;
    }
}
