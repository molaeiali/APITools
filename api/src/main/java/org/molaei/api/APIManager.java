package org.molaei.api;

import android.app.Activity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIManager {

    protected static List<Header> defaultHeaders;

    private static APIManager instance;

    protected APIManager(){
        defaultHeaders = new ArrayList<>();

    }

    public void POST(Activity activity, List<Header> headers, String url, JSONObject jsonObject, AsyncWorks asyncWorks){
        List<Header> newHeaders = new ArrayList<>();
        newHeaders.addAll(defaultHeaders);
        newHeaders.addAll(headers);
        APITools.generalAsyncTask(activity,newHeaders,url,jsonObject, asyncWorks);
    }

    public void GET(Activity activity, List<Header> headers, String url, AsyncWorks asyncWorks){
        List<Header> newHeaders = new ArrayList<>();
        newHeaders.addAll(defaultHeaders);
        newHeaders.addAll(headers);
        APITools.generalAsyncTask(activity,newHeaders,url, asyncWorks);
    }

}
