package com.webapp.offerSRkh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webapp.offerSRkh.CategorywiseNewsActivity;
import com.webapp.offerSRkh.Model.CategoryModel;
import com.webapp.offerSRkh.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    List<CategoryModel> data;
    Context context;

    public CategoryAdapter(List<CategoryModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.category_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        CategoryModel categoryModel = data.get(position);

        holder.textView.setText(categoryModel.getCname());
        Glide.with(holder.imageView.getContext()).load(IMAGE_URL+categoryModel.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CategorywiseNewsActivity.class);
                intent.putExtra("C_NAME",holder.textView.getText());
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cimage);
            textView = itemView.findViewById(R.id.cname);
            Typeface tf = Typeface.createFromAsset(itemView.getContext().getAssets(), "Poppins-Medium.ttf");
            textView.setTypeface(tf);

        }
    }
}
