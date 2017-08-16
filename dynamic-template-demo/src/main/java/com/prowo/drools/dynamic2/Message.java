package com.prowo.drools.dynamic2;

import java.io.Serializable;

/**
 * Created by linan on 2017/8/15.
 */
public class Message implements Serializable {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status='" + status + '\'' +
                '}';
    }
}
