package com.community.jboss.visitingcard.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.model.Contributor;

import java.util.ArrayList;

public class ItemArrayAdapter extends ArrayAdapter<Contributor> {

    private int listItemLayout;
    public ItemArrayAdapter(Context context, int layoutId, ArrayList<Contributor> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contributor item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(listItemLayout, parent, false);
        viewHolder.avatar = view.findViewById(R.id.c_avatar);
        viewHolder.name = view.findViewById(R.id.c_name);
        viewHolder.commits = view.findViewById(R.id.n_commits);

        // Populate the data into the template view using the data object
        Glide.with(getContext()).load(item.getAvatarUrl()).into(viewHolder.avatar);
        viewHolder.name.setText(item.getName());
        viewHolder.commits.setText(Integer.toString(item.getContibutions()));

        // Return the completed view to render on screen
        return view;
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        private ImageView avatar;
        private TextView name, commits;
    }
}