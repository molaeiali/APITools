package org.molaei.api;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;


class APITools {

    private APITools(){

    }

    private static String POST(String urlString, JSONObject jsonObject, List<Header> headers) {
        String result = "";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            String json = jsonObject.toString();

            System.setProperty("http.keepAlive", "false");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(30000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(json.getBytes().length);

            //HTTP header
//            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            if (headers != null) {
                for (Header header : headers) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            //open
            connection.connect();

            //setup send
            outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(json.getBytes());
            //clean up
            outputStream.flush();

            //do somehting with response
            inputStream = connection.getInputStream();
            result = convertInputStreamToString(inputStream);
        } catch (Exception e) {
            if(e.getClass().equals(FileNotFoundException.class)){
                result = "404 Not Found";
            }
            if(e.getClass().equals(SSLHandshakeException.class)){
                result = "SSL Problem";
            }
            e.printStackTrace();
        } finally {
            try {
                //clean up
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private static String GET(String urlString, List<Header> headers) {
        String result = "";
        HttpURLConnection httpConn = null;
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            System.setProperty("http.keepAlive", "false");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setReadTimeout(20000);
            httpConn.setConnectTimeout(30000);
            httpConn.setRequestMethod("GET");

            if (headers != null) {
                for (Header header : headers) {
                    httpConn.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            httpConn.connect();

            inputStream = httpConn.getInputStream();
            result = convertInputStreamToString(inputStream);

        } catch (Exception e) {
            if(e.getClass().equals(FileNotFoundException.class)){
                result = "404 Not Found";
            }
            if(e.getClass().equals(SSLHandshakeException.class)){
                result = "SSL Problem";
            }
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        String string = "";
        if (inputStream != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            BufferedReader r1 = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = r1.readLine()) != null) {
                sb.append(line).append("\n");
            }

            string = sb.toString();
        }
        return string;
    }

    static void generalAsyncTask(Activity activity, List<Header> headers, String url, AsyncWorks asyncWorks) {
        new GeneralAsyncTask(activity, headers, asyncWorks, null).execute(url);
    }

    static void generalAsyncTask(Activity activity, List<Header> headers, String url, JSONObject jsonObject, AsyncWorks asyncWorks) {
        new GeneralAsyncTask(activity, headers, asyncWorks, jsonObject).execute(url);
    }


    private static class GeneralAsyncTask extends AsyncTask<String, Void, String> {
        private AsyncWorks asyncWorks;
        private JSONObject jsonObject;
        private Activity activity;
        private List<Header> headers;

        GeneralAsyncTask(Activity activity, List<Header> headers, AsyncWorks asyncWorks, JSONObject jsonObject) {
            this.asyncWorks = asyncWorks;
            this.jsonObject = jsonObject;
            this.activity = activity;
            this.headers = headers;
        }

        private boolean hasProtocol(String url){
            for(int i=0;i<url.length()-3;i++){
                if(url.substring(i,i+3).equals("://")){
                    return true;
                }
            }
            return false;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = hasProtocol(strings[0])?strings[0]:String.format("%s%s","http://",strings[0]);
            if (jsonObject == null) {
                return APITools.GET(url, headers);
            } else {
                return APITools.POST(url, jsonObject, headers);
            }
        }

        @Override
        protected void onPostExecute(String gotJSON) {
            if (activity.isFinishing()) {
                return;
            }
            asyncWorks.postExecute(gotJSON);
        }

        @Override
        protected void onPreExecute() {
            asyncWorks.preExecute();
        }
    }
}
