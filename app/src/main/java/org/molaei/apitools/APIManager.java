package org.molaei.apitools;


import android.app.Activity;

import org.molaei.api.Header;
import org.molaei.api.Loading;

public class APIManager extends org.molaei.api.APIManager {

    private static APIManager instance;

    private APIManager() {
        super();
        defaultHeaders.add(new Header("Content-Type","application/json;charset=utf-8"));
        defaultHeaders.add(new Header("X-Requested-With", "XMLHttpRequest"));

    }

    @Override
    public Loading getLoading(Activity activity) {
        //if you dont want a loading view
        //return null;
        //else return an object of org.molaei.api.Loading implemented class
        return new LoadingView(activity);
    }

    public static APIManager getInstance(){
        if(instance == null){
            instance = new APIManager();
        }
        return instance;
    }

}
