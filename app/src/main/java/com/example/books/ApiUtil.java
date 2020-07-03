package com.example.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}
    public static final String BASE_API_UTIL="https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY="q";
    public static final String KEY="key";
    public static final String API_KEY="AIzaSyA0OFLH0s3GXlOa8b1I8qjoaCaSqkTX3DM";
    public static final String TITLE="intitle:";
    public static final String PUBLISHER="inpublisher:";
    public static final String AUTHOR="inauthor:";
    public static final String ISBN="isbn:";
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
    public static URL buildUrl(String title,String author,String publisher,String isbn){
        URL url=null;
        StringBuilder sb=new StringBuilder();
        if(!title.isEmpty())sb.append(TITLE + title + "+");
        if(!author.isEmpty())sb.append(AUTHOR + author + "+");
        if(!publisher.isEmpty())sb.append(PUBLISHER + publisher+ "+");
        if(!isbn.isEmpty())sb.append(ISBN + isbn + "+");
        sb.setLength(sb.length()-1);
        String query=sb.toString();
        Uri uri=Uri.parse(BASE_API_UTIL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,query)
                .appendQueryParameter(KEY,API_KEY)
                .build();
        try {
            url=new URL(uri.toString());
        } catch (Exception e) {
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
    public static ArrayList<Book> getBooksFromJSON(String json){
        final String ID="id";
        final String TITLE="title";
        final String SUBTITLE="subtitle";
        final String AUTHOR="authors";
        final String PUBLISHER="publisher";
        final String PUBLISHDATE="publishedDate";
        final String ITEMS="items";
        final String VOLUMEINFO="volumeInfo";
        final String DESCRIPTION="description";
        final String THUMBNAIL="thumbnail";
        final String IMAGELINKS="imageLinks";
        ArrayList<Book> books=new ArrayList<Book>();
        try {
            //create a json object
            JSONObject JSONBooks=new JSONObject(json);
            //get array that contains all the books
            JSONArray arrayBooks=JSONBooks.getJSONArray(ITEMS);
            //to get number of books
            int numberOfBooks=arrayBooks.length();
            //looping through the books
            for (int i=0;i<numberOfBooks;i++)
            {
                JSONObject bookJSON=arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON=bookJSON.getJSONObject(VOLUMEINFO);
                JSONObject imagelinks=null;
                if (volumeInfoJSON.has(IMAGELINKS)){
                    imagelinks=volumeInfoJSON.getJSONObject(IMAGELINKS);
                }

                //get the number of authors(are arrays)
                int authoNum;
                try {
                     authoNum=volumeInfoJSON.getJSONArray(AUTHOR).length();
                } catch (JSONException e) {
                    e.printStackTrace();
                    authoNum=0;

                }

                //create array of string that will contain the authors
                String[] authors=new String[authoNum];
                //lets loop through the authors and add author names to our string array
                for (int j=0;j<authoNum;j++)
                {
                    authors[j]=volumeInfoJSON.getJSONArray(AUTHOR).get(j).toString();
                }
                //create a new Book retrieving all the data we need
                Book book=new Book(bookJSON.getString(ID),volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(PUBLISHER)),
                        (volumeInfoJSON.isNull(PUBLISHDATE)?"":volumeInfoJSON.getString(PUBLISHDATE)),
                        volumeInfoJSON.getString(DESCRIPTION),imagelinks.getString(THUMBNAIL));
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }
}
