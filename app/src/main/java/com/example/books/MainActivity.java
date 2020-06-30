package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView tvResult;
    ProgressBar mProgressBar;
    RecyclerView recyclerbook;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerbook=findViewById(R.id.recybooks);
        mLinearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerbook.setLayoutManager(mLinearLayoutManager);


        try {
            URL url=ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(url);

        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("error",e.getMessage());
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookurl=ApiUtil.buildUrl(query);
            new BooksQueryTask().execute(bookurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
                recyclerbook.setVisibility(View.INVISIBLE);
                tvResult.setVisibility(View.VISIBLE);
            }
            else {
                tvResult.setVisibility(View.INVISIBLE);
                recyclerbook.setVisibility(View.VISIBLE);

            }
            ArrayList<Book> books=ApiUtil.getBooksFromJSON(result);
            String resultString="";
           BookRecyclerviewAdapter bookRecyclerviewAdapter=new BookRecyclerviewAdapter(MainActivity.this,books);
            recyclerbook.setAdapter(bookRecyclerviewAdapter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar=findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        final MenuItem menuItem=menu.findItem(R.id.searchbook);
        final SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }
}
