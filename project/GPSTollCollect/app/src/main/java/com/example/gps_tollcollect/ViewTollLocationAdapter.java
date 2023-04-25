package com.example.gps_tollcollect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ViewTollLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<TollLocation> material = Collections.emptyList();
    int currentPos = 0;

    public ViewTollLocationAdapter(Context context, List<TollLocation> material) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.material = material;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_view_toll_place_adapter, parent, false);
        ViewTollLocationAdapter.MyHolder holder = new ViewTollLocationAdapter.MyHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewTollLocationAdapter.MyHolder myHolder = (ViewTollLocationAdapter.MyHolder) holder;
        TollLocation current = material.get(position);
        myHolder.plan_name1.setText(current.name);


    }

    @Override
    public int getItemCount() {

        return material.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView plan_name1;


        public MyHolder(View itemView) {
            super(itemView);
            plan_name1 = (TextView) itemView.findViewById(R.id.planname1);

        }
    }
}