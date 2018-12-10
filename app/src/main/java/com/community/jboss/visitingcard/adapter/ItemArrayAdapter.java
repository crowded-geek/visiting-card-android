package com.community.jboss.visitingcard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.ProfileActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.model.CardListItem;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ItemArrayAdapter extends ArrayAdapter<CardListItem> {

    private int listItemLayout;
    public ItemArrayAdapter(Context context, int layoutId, ArrayList<CardListItem> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CardListItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(listItemLayout, parent, false);

            viewHolder.avatar = convertView.findViewById(R.id.li_image);
            viewHolder.name = convertView.findViewById(R.id.li_name);
            viewHolder.email = convertView.findViewById(R.id.li_email);
            viewHolder.save = convertView.findViewById(R.id.li_btn);
            convertView.setTag(viewHolder); // view lookup cache stored in tag
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        Glide.with(getContext()).load(item.getAvatarUrl()).into(viewHolder.avatar);
        viewHolder.name.setText(item.getName());
        viewHolder.email.setText(item.getEmail());
        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("cardData", item.getUid());
                getContext().startActivity(i);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        CircularImageView avatar;
        TextView name, email;
        Button save;
    }
}