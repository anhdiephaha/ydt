package com.ydt.payload;

public class Payload {
    private String status;
    private String messsage;
    private Object data;

    public Payload() {
    }

    public Payload(String status, String messsage, Object data) {
        this.status = status;
        this.messsage = messsage;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
