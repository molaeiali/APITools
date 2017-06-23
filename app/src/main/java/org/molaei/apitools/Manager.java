package org.molaei.apitools;

import org.molaei.api.Parser;

public class Manager extends Parser {

    private static Manager instance;

    private Manager() {
        super();
    }

    public static Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }
        return instance;
    }

    public boolean login(){
        apiSuccess = true;
        return true;
    }
}
