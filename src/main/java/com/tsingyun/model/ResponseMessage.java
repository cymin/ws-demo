package com.tsingyun.model;

import java.io.Serializable;

/**
 * server send message to browser
 */
public class ResponseMessage implements Serializable{

    public ResponseMessage(){}

    public ResponseMessage(String message){
        this.responseMessage = message;
    }

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
