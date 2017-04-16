package com.animelabs.gopaisasampleproject.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.animelabs.gopaisasampleproject.AppController.ApplicationController;
import com.animelabs.gopaisasampleproject.Model.Entry;
import com.animelabs.gopaisasampleproject.R;

import java.util.ArrayList;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListHolder> {

    private ArrayList<Entry> entryArrayList;
    private ImageLoader imageLoader;

    public AppListAdapter(ArrayList<Entry> entryArrayList){
        this.entryArrayList = entryArrayList;
        this.imageLoader = ApplicationController.getInstance().getImageLoader();
    }

    @Override
    public AppListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_list_item, parent, false);
        return new AppListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppListHolder holder, int position) {
        Entry entry = entryArrayList.get(position);
        holder.appName.setText(entry.getName().getLabel());
        holder.appSummary.setText(entry.getSummary().getLabel());
        String price = entry.getPrice().getPriceAttributes().getAmount().substring(0,3) + " " + entry.getPrice().getPriceAttributes().getCurrency();
        holder.price.setText(price);
        holder.imageIcon.setImageUrl(entry.getImage().getLabel(),imageLoader);
    }

    @Override
    public int getItemCount() {
        return entryArrayList.size();
    }

    public class AppListHolder extends RecyclerView.ViewHolder{
        TextView appName, appSummary, price;
        NetworkImageView imageIcon;
        public AppListHolder(View itemView) {
            super(itemView);
            appName = (TextView)itemView.findViewById(R.id.textView3);
            appSummary = (TextView)itemView.findViewById(R.id.textView4);
            price = (TextView)itemView.findViewById(R.id.textView5);
            imageIcon = (NetworkImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
