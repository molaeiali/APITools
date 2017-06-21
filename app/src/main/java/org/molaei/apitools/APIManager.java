package org.molaei.apitools;


import org.molaei.api.Header;

public class APIManager extends org.molaei.api.APIManager {

    private static APIManager instance;

    private APIManager() {
        super();
        defaultHeaders.add(new Header("Content-Type","application/json;charset=utf-8"));
        defaultHeaders.add(new Header("X-Requested-With", "XMLHttpRequest"));

    }

    public static APIManager getInstance(){
        if(instance == null){
            instance = new APIManager();
        }
        return instance;
    }


}
