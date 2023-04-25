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

public class ViewUserFineAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<PaidFine> material = Collections.emptyList();
    int currentPos = 0;

    public ViewUserFineAdapter1(Context context, List<PaidFine> material) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.material = material;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_view_user_fine_adapter1, parent, false);
        ViewUserFineAdapter1.MyHolder holder = new ViewUserFineAdapter1.MyHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewUserFineAdapter1.MyHolder myHolder = (ViewUserFineAdapter1.MyHolder) holder;
        PaidFine current = material.get(position);
        myHolder.plan_name1.setText(current.vehicle_no);
        myHolder.plan_name2.setText("Fine Amount : "+current.amount);
        myHolder.plan_name3.setText("Fine : "+current.fine_type);
        myHolder.plan_name4.setText("Paid On : ₹"+current.payment_date);




    }

    @Override
    public int getItemCount() {

        return material.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView plan_name1,plan_name2,plan_name3,plan_name4;


        public MyHolder(View itemView) {
            super(itemView);
            plan_name1 = (TextView) itemView.findViewById(R.id.planname1);
            plan_name2 = (TextView) itemView.findViewById(R.id.planname2);
            plan_name3 = (TextView) itemView.findViewById(R.id.planname3);
            plan_name4 = (TextView) itemView.findViewById(R.id.planname4);

        }
    }
}