package com.webapp.offerSRkh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webapp.offerSRkh.FullNewsActivity;
import com.webapp.offerSRkh.Model.NewsModel;
import com.webapp.offerSRkh.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;

public class NewshomeAdapter extends RecyclerView.Adapter<NewshomeAdapter.viewHolder> {
    Context context;
    private List<NewsModel> items = new ArrayList<>();
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public NewshomeAdapter(Context context, List<NewsModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_ITEM) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
//            return new viewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.news_item, parent, false);
            return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, final int position) {

  //      if(holder instanceof viewHolder) {
            final NewsModel model = items.get(position);

            holder.newsHeading.setText(model.getNews_heading());
            holder.content.setText(model.getNewsContent());
            holder.date.setText(model.getDate());
            holder.time.setText(model.getTime());
            Glide.with(holder.imageView.getContext()).load(IMAGE_URL + model.getNewsImg()).into(holder.imageView);
            if (holder.content.getText().toString().equals("null")) {
                holder.content.setVisibility(View.INVISIBLE);
            }
            String id = items.get(position).getId();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullNewsActivity.class);
                    intent.putExtra("image", model.getNewsImg());
                    intent.putExtra("heading", model.getNews_heading());
                    intent.putExtra("content", model.getNewsContent());
                    intent.putExtra("time", model.getTime());
                    intent.putExtra("url", model.getUrl());
                    intent.putExtra("id", id);
                    intent.putExtra("date", model.getDate());
                    intent.putExtra("btn_name", model.getBtn_name());
                    intent.putExtra("btn_url", model.getBtn_url());
                    intent.putExtra("position", holder.getAdapterPosition());
                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

   public class viewHolder extends RecyclerView.ViewHolder{
        TextView time, date,newsHeading;
        HtmlTextView content;
        ImageView imageView;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            newsHeading = itemView.findViewById(R.id.newshead);
            imageView = itemView.findViewById(R.id.newsImage);
            content = itemView.findViewById(R.id.content);
        }
   }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull final View itemView1) {
            super(itemView1);
            progressBar = itemView1.findViewById(R.id.progressBar1);
        }
    }
}




