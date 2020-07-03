package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {
   // EditText etauthor,etisbn,etpublisher,ettitle;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etauthor=findViewById(R.id.author);
        final EditText etisbn=findViewById(R.id.isbn);
        final EditText etpublisher=findViewById(R.id.publisher);
        final EditText ettitle=findViewById(R.id.title);
        btnSearch=findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=ettitle.getText().toString().trim();
                String author=etauthor.getText().toString().trim();
                String publisher=etpublisher.getText().toString().trim();
                String isbn=etisbn.getText().toString().trim();
                if (title.isEmpty()&& author.isEmpty()&& publisher.isEmpty()&& isbn.isEmpty())
                {
                    Toast.makeText(SearchActivity.this, "no search data", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    URL queryUrl=ApiUtil.buildUrl(title,author,publisher,isbn);
                    //sharedpreferences
                    Context context=getApplicationContext();
                    int position=SpUtil.getPreferenceInt(context,SpUtil.POSITION);
                    if (position==0||position==5)
                    {
                        position=1;
                    }
                    else {
                        position++;
                    }
                    String key=SpUtil.QUERY+String.valueOf(position);
                    String value=title+ ","+author+","+publisher+","+isbn;
                    SpUtil.setPreferenceString(context,key,value);
                    SpUtil.setPreferenceInt(context,SpUtil.POSITION,position);
                    Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                    intent.putExtra("Query",queryUrl.toString());
                    startActivity(intent);
                }
            }
        });
    }
}
