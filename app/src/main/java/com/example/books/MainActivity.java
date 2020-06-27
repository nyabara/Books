package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tvResponse,tvResult;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResponse=findViewById(R.id.tvResponse);
        try {
            URL url=ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(url);

        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("error",e.getMessage());
        }

    }
    public class BooksQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchResultUrl=urls[0];
            String result=null;
            try {
                 result=ApiUtil.getJson(searchResultUrl);

            } catch (IOException e) {
                Log.d("error",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.INVISIBLE);
            tvResult=findViewById(R.id.tvResult);
            if (result==null)
            {
                tvResponse.setVisibility(View.INVISIBLE);
                tvResult.setVisibility(View.VISIBLE);
            }
            else {
                tvResult.setVisibility(View.INVISIBLE);
                tvResponse.setVisibility(View.VISIBLE);

            }
            tvResponse.setText(result);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar=findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
