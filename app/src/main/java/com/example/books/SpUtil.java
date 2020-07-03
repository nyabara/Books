package com.example.books;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SpUtil {
    private SpUtil(){}
    //constant for preference name
    public static final String PRE_NAME="BooksPreference";
    //constants to check queries
    public static final String POSITION="position";
    public static final String QUERY="query";
    //method for initialization of preference
    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
    }
    //method to read string from the preferences
    public static String getPreferenceString(Context context,String key){
        return getPrefs(context).getString(key,"");
    }
    //method to read int from the preferences
    public static int getPreferenceInt(Context context,String key){
        return getPrefs(context).getInt(key,0);
    }
    //method to write a string
    public static void setPreferenceString(Context context,String key,String value){
        //retrieve editor object
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(key,value);
        //commit changes
        editor.apply();
    }
    //method to write a string
    public static void setPreferenceInt(Context context,String key,int value){
        //retrieve editor object
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putInt(key,value);
        //commit changes
        editor.apply();
    }
    public static ArrayList<String> getQueryList(Context context)
    {
        ArrayList<String> queryList=new ArrayList<>();
        for (int i=1;i<=5;i++)
        {
            String query=getPrefs(context).getString(QUERY+String.valueOf(i),"");
            if (!query.isEmpty()){
                query=query.replace(",","");
                queryList.add(query.trim());
            }
        }
        return queryList;
    }
}
