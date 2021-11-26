package com.webapp.offerSRkh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webapp.offerSRkh.FullNewsActivity;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;

public class CategorywiseAdapter extends RecyclerView.Adapter<CategorywiseAdapter.viewHolder> {

   // List<CategorywiseModel> categorywiseModelList;
   Context context;
    List<NewsModel> list;

    public CategorywiseAdapter(Context context, List<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.news_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final NewsModel newsModel = list.get(position);

        holder.newsHeading.setText(newsModel.getNews_heading());
        holder.time.setText(newsModel.getTime());
        holder.date.setText(newsModel.getDate());

        // holder.content.setContentDescription(newsModel.getNewsContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(Html.fromHtml(newsModel.getNewsContent().toString(), Html.FROM_HTML_MODE_LEGACY));
        } else{
            holder.content.setText(Html.fromHtml(newsModel.getNewsContent().toString()));
        }
        Glide.with(holder.imageView.getContext()).load(IMAGE_URL+newsModel.getNewsImg()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullNewsActivity.class);
                intent.putExtra("image",newsModel.getNewsImg() );

                intent.putExtra("heading",newsModel.getNews_heading());
                intent.putExtra("content",newsModel.getNewsContent());
//                intent.putExtra("time",newsModel.getTime());
//               intent.putExtra("date",newsModel.getDate());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView time, date,newsHeading;
        TextView content;
        ImageView imageView;
        String cat_id;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            newsHeading = itemView.findViewById(R.id.newshead);
            imageView = itemView.findViewById(R.id.newsImage);
            content = itemView.findViewById(R.id.content);

        }
    }
}
