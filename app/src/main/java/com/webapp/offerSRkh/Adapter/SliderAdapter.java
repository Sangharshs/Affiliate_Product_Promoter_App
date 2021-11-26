package com.webapp.offerSRkh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.webapp.offerSRkh.Model.SliderModel;
import com.webapp.offerSRkh.R;

import java.util.List;

import static com.webapp.offerSRkh.Apiconfig.IMAGE_URL;

public class SliderAdapter extends PagerAdapter {
    List<SliderModel> sliderModelList;

    public SliderAdapter(List<SliderModel> sliderModelList) {
        this.sliderModelList =sliderModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ImageView banner = view.findViewById(R.id.banner_slide);
        Glide.with(container.getContext()).load(IMAGE_URL+sliderModelList.get(position).getBanner()).into(banner);
        container.addView(view,0);
      return  view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }
}
