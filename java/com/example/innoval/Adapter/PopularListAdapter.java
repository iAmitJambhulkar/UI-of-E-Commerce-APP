package com.example.innoval.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.innoval.Activity.DetailActivity;
import com.example.innoval.Domain.PopularDomain;
import com.example.innoval.R;

import java.util.ArrayList;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.Viewholder> {
    ArrayList<PopularDomain> items;
    Context context;

    public PopularListAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListAdapter.Viewholder holder, int position) {
    holder.titletext.setText(items.get(position).getTitle());
    holder.feetext.setText("$"+items.get(position).getPrice());
    holder.reviewtext.setText(""+items.get(position).getReview());
    holder.scoretext.setText(""+items.get(position).getScore());

    int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(),"drawable",
            holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.macimg);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object",items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView titletext, feetext, scoretext, reviewtext;
        ImageView macimg;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.titletext);
            feetext = itemView.findViewById(R.id.feetext);
            scoretext = itemView.findViewById(R.id.numberItemText);
            reviewtext = itemView.findViewById(R.id.reviewtext);
            macimg = itemView.findViewById(R.id.macimg);

        }
    }
}
