package com.example.afe.task.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.afe.task.R;
import com.example.afe.task.model.Task;
import java.util.ArrayList;

public  class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.ViewHolder>{
    private ArrayList<Task> mTache=new ArrayList<>();
    private ArrayList<Task> mTache;
    private Context mContext;
    private OnTaskListener mOnTaskListener;

    public TacheAdapter(ArrayList<Task> mNomTache, Context mContext, OnTaskListener onTaskListener) {
        this.mTache = mNomTache;
        this.mContext = mContext;
        this.mOnTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,parent,false);
        ViewHolder holder= new ViewHolder(v, mOnTaskListener);
        return holder;
    }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int prio = mTache.get(position).getPriorite();

            String priorityName;
            if (prio== 1){
                priorityName = "High";
            } else {
                if (prio == 2){
                    priorityName = "Default";
                } else {
                    priorityName = "Low";
                }
            }
        if(mTache.get(position).getName().length()>6) {
            holder.nom_tache.setText(mTache.get(position).getName().substring(0, 5) + "...");
        }
        else {
            holder.nom_tache.setText(mTache.get(position).getName());
        }
        if(mTache.get(position).getDescription().length()>6) {
            holder.desc_tache.setText(mTache.get(position).getDescription().substring(0, 5) + "...");
        }
        else{
            holder.desc_tache.setText(mTache.get(position).getDescription());
        }
        holder.priority_tache.setText(priorityName);


       if((mTache.get(position).getDateFin().getTime()< System.currentTimeMillis())&&(mTache.get(position).getTermine()==false)){

              holder.tache_layout.setBackgroundColor(Color.parseColor("#ff4444"));
        }
        else if((mTache.get(position).getTermine())&&mTache.get(position).isAtemps()==false){
            holder.tache_layout.setBackgroundColor(Color.parseColor("#ffbb33"));
        }
        else if((mTache.get(position).getTermine())&&mTache.get(position).isAtemps()){
           holder.tache_layout.setBackgroundColor(Color.parseColor("#00C851"));
       }
       else{
           holder.tache_layout.setBackgroundColor(Color.parseColor("#ffffff"));
       }



    }

    @Override
    public int getItemCount() {
        return mTache.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nom_tache;
        TextView desc_tache;
        TextView priority_tache;
        RelativeLayout tache_layout;
        OnTaskListener onTaskListener;
        public ViewHolder(View itemView, OnTaskListener onTaskListener) {
            super(itemView);

            nom_tache=itemView.findViewById(R.id.name);
            desc_tache=itemView.findViewById(R.id.description);
            priority_tache=itemView.findViewById(R.id.priorityd);
            tache_layout=itemView.findViewById(R.id.layout_list);
            this.onTaskListener = onTaskListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }
    public interface OnTaskListener{
        void onTaskClick(int position);
    }
}
