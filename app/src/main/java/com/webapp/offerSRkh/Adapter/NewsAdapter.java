package com.webapp.offerSRkh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.webapp.offerSRkh.FullNewsActivity;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> implements Filterable {

    Context context;
    List<NewsModel> list;
    List<NewsModel> save_news_list;
    List<NewsModel> search_news_list;

    public NewsAdapter(Context context, List<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.news_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        final NewsModel newsModel = list.get(position);
        holder.newsHeading.setText(newsModel.getNews_heading());
        holder.time.setText(newsModel.getTime());
        holder.date.setText(newsModel.getDate());
        holder.content.setText(newsModel.getNewsContent());

        if(holder.content.getText().toString().equals("null") ){
            holder.content.setVisibility(View.GONE);
        }

        Glide.with(holder.imageView.getContext()).load(IMAGE_URL+newsModel.getNewsImg()).into(holder.imageView);


        String id = list.get(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(context, FullNewsActivity.class);
                    intent.putExtra("image", newsModel.getNewsImg());
                    intent.putExtra("heading", newsModel.getNews_heading());
                    intent.putExtra("content", newsModel.getNewsContent());
                    intent.putExtra("time", newsModel.getTime());
                    intent.putExtra("url", newsModel.getUrl());
                    intent.putExtra("id",id);
                    intent.putExtra("date", newsModel.getDate());
                    intent.putExtra("btn_name",newsModel.getBtn_name());
                    intent.putExtra("btn_url",newsModel.getBtn_url());
                    intent.putExtra("position",  holder.getAdapterPosition());

                    view.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    class viewHolder extends RecyclerView.ViewHolder{
        TextView time, date,newsHeading;
        HtmlTextView content;
        ImageView imageView;
        ImageButton imageButton;
        ToggleButton toggleButton;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            newsHeading = itemView.findViewById(R.id.newshead);
            imageView = itemView.findViewById(R.id.newsImage);
            content = itemView.findViewById(R.id.content);


        }
    }

}
