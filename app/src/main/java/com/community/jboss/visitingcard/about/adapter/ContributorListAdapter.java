package com.community.jboss.visitingcard.about.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.about.models.ContributionModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ContributorListAdapter extends RecyclerView.Adapter<ContributorListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ContributionModel> list;
    private Boolean theme;

    public ContributorListAdapter(ArrayList<ContributionModel> list, Context context, Boolean theme) {
        this.list = list;
        this.context = context;
        this.theme = theme;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributorlistitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Name.setText(list.get(position).getName());
        holder.Handle.setText(("@" + list.get(position).getName()));
        Glide.with(context)
                .load(list.get(position).getAvatar())
                .into(holder.Userimage);
        if (theme) {
            holder.Name.setTextColor(Color.WHITE);
            holder.Handle.setTextColor(Color.WHITE);
            holder.mcardview.setCardBackgroundColor(Color.parseColor("#212121"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView Name;
        public AppCompatTextView Handle;
        public CircularImageView Userimage;
        public CardView mcardview;

        public ViewHolder(View itemView) {
            super(itemView);

            Name = (AppCompatTextView) itemView.findViewById(R.id.contributorname);
            Handle = (AppCompatTextView) itemView.findViewById(R.id.contributorhandle);
            Userimage = itemView.findViewById(R.id.contributorimage);
            mcardview = itemView.findViewById(R.id.contributor_card_view);


        }


    }
}
