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

public class ViewFeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
private Context context;
private LayoutInflater inflater;
        List<Feedback> feed= Collections.emptyList();
        int currentPos=0;

public ViewFeedbackAdapter(Context context, List<Feedback> feed){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.feed=feed;

        }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=inflater.inflate(R.layout.activity_view_feedback_adapter,parent,false);
        MyHolder holder=new MyHolder(view);


        return holder;
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position){

        MyHolder myHolder=(MyHolder)holder;
        Feedback current=feed.get(position);
        myHolder.feedback_details.setText(current.feedback_details);
        myHolder.feedback_date.setText("Feedback Date : "+current.feedback_date);
        myHolder.reply.setText("Reply : "+current.reply);



}

@Override
public int getItemCount(){

        return feed.size();
        }

class MyHolder extends RecyclerView.ViewHolder {
    TextView feedback_details;
    TextView feedback_date;
    TextView reply;



    public MyHolder(View itemView) {
        super(itemView);
        feedback_details = (TextView) itemView.findViewById(R.id.tFeedback);
        feedback_date = (TextView) itemView.findViewById(R.id.tFeedBackDate);
        reply = (TextView) itemView.findViewById(R.id.tReply);

    }
}

}