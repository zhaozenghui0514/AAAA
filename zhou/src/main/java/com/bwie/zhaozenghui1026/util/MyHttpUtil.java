package com.bwie.zhaozenghui1026.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHttpUtil {

    public  static Boolean HasNewWork(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return  activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }
    public static String getHttp(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 200){
                String s = stream2String(httpURLConnection.getInputStream());
                return s;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    private static String stream2String(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = null;
        while ((s = bufferedReader.readLine()) != null){
            stringBuffer.append(s);
        }
        bufferedReader.close();
        return stringBuffer.toString();
    }

    public static <T> T getGson(String s, Class cl){
        String http = getHttp(s);
        return (T) new Gson().fromJson(http,cl);
    }
}
