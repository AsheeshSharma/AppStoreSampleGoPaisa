package com.animelabs.gopaisasampleproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animelabs.gopaisasampleproject.Adapter.AppListAdapter;
import com.animelabs.gopaisasampleproject.Model.Entry;
import com.animelabs.gopaisasampleproject.R;

import java.util.ArrayList;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class AppListFragment extends Fragment {
    private Context context;
    private ArrayList<Entry> entryArrayList;
    private AppListAdapter appListAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_list,container, false );
        appListAdapter = new AppListAdapter(entryArrayList);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(appListAdapter);
        return view;

    }

    public AppListFragment(){

    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setEntryArrayList(ArrayList<Entry> entryArrayList){
        this.entryArrayList = entryArrayList;
    }
}
