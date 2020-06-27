package com.example.books;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}
    public static final String BASE_API_UTIL="https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY="q";
    public static final String KEY="key";
    public static final String API_KEY="AIzaSyALGCPv1jXZLdJOm_a8nDam-_u0TN1DM6g";
    public static URL buildUrl(String title){

        URL url=null;
        Uri uri= Uri.parse(BASE_API_UTIL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,title)
                .appendQueryParameter(KEY,API_KEY)
                .build();
        try {
            url=new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        try {
            InputStream stream=connection.getInputStream();
            Scanner scanner=new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasdata=scanner.hasNext();
            if (hasdata){
                return scanner.next();
            }
            else {
                return null;
            }
        }catch (Exception e)
        {
            //e.getMessage();
            Log.d("error",e.getMessage());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }
}
