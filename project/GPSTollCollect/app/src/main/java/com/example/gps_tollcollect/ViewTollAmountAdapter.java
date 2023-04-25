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

public class ViewTollAmountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<TollAmount> material = Collections.emptyList();
    int currentPos = 0;

    public ViewTollAmountAdapter(Context context, List<TollAmount> material) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.material = material;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_view_toll_amount_adapter, parent, false);
        ViewTollAmountAdapter.MyHolder holder = new ViewTollAmountAdapter.MyHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewTollAmountAdapter.MyHolder myHolder = (ViewTollAmountAdapter.MyHolder) holder;
        TollAmount current = material.get(position);
        myHolder.plan_name1.setText(current.vehicle_type);
        myHolder.plan_name2.setText("Journey Ticket : "+current.journey_ticket);
        myHolder.plan_name3.setText("Amount : ₹"+current.amount);



    }

    @Override
    public int getItemCount() {

        return material.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView plan_name1,plan_name2,plan_name3;


        public MyHolder(View itemView) {
            super(itemView);
            plan_name1 = (TextView) itemView.findViewById(R.id.planname1);
            plan_name2 = (TextView) itemView.findViewById(R.id.planname2);
            plan_name3 = (TextView) itemView.findViewById(R.id.planname3);


        }
    }
}