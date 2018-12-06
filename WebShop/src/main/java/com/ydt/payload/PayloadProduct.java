package com.ydt.payload;

import java.util.List;

public class PayloadProduct {
    private Object data;

    public PayloadProduct() {
    }

    public PayloadProduct(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
