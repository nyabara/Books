package com.example.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookRecyclerviewAdapter extends RecyclerView.Adapter<BookRecyclerviewAdapter.ViewHolder> {
    private final Context mContext;
    public final LayoutInflater mLayoutInflater;
    public final ArrayList<Book>mBooks;

    public BookRecyclerviewAdapter(Context context, ArrayList<Book> books) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBooks = books;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mLayoutInflater.inflate(R.layout.book_list_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book=mBooks.get(position);
        holder.textTitle.setText(book.title);
        String authors="";
        int i=0;
        for (String author:book.author)
        {
            authors+=author;
            i++;
            if (i<book.author.length)
            {
                authors+=",";
            }
        }
        holder.textAuthor.setText(authors);
        holder.textPublish.setText(book.publisher);
        holder.textpublishedDate.setText(book.publishDate);

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textTitle,textAuthor,textPublish,textpublishedDate;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textTitle=itemView.findViewById(R.id.textTitle);
        textAuthor=itemView.findViewById(R.id.textAuthor);
        textPublish=itemView.findViewById(R.id.textPublish);
        textpublishedDate=itemView.findViewById(R.id.textpublishDate);
    }
}
}
