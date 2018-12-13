package com.community.jboss.visitingcard.IntroScreens;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.community.jboss.visitingcard.R;

class SliderAdapter extends PagerAdapter {

    private final int numberOfItemsInSlider;
    private Context context;

    private int[] slide_images = {
            R.drawable.icon,
            R.drawable.networking,
            R.drawable.location,
            R.drawable.discussion
    };

    private String[] slide_text = {
            "Welcome to Visiting Card Android",
            "Network better with people at conferences and meetups",
            "Share your business cards with people around you",
            "Explore other business opportunities"
    };

    SliderAdapter(Context context, int numberOfItemsInSlider) {
        this.context = context;
        this.numberOfItemsInSlider = numberOfItemsInSlider;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.tutorial_pager_layout, container, false);
        }

        ImageView slideImageView = view.findViewById(R.id.imageView);
        TextView slideTextView = view.findViewById(R.id.textView);

        slideImageView.setImageResource(slide_images[position]);
        slideTextView.setText(slide_text[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return numberOfItemsInSlider;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
