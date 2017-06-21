package org.molaei.apitools;


import android.support.v7.app.AppCompatActivity;

import org.molaei.api.AsyncWorks;

public class Usage extends AppCompatActivity{

    public Usage() {
        APIManager.getInstance().GET(this, null, "URL", new AsyncWorks() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(String jsonToParse) {

            }
        });
    }
}
