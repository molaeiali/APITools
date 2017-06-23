package org.molaei.api;

public class Parser {

    protected boolean apiSuccess;
    protected String apiMessage;
    protected Parser(){
        apiMessage = "";
        apiSuccess = false;
    }

    public boolean isApiSuccess() {
        return apiSuccess;
    }

    public String getApiMessage() {
        return apiMessage;
    }

}
