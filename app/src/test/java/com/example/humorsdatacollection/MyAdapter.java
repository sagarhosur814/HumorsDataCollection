package com.example.humorsdatacollection;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHoder> {
    private ArrayList<Model>modelArrayList;
    private Context context;

    //constructor

    public MyAdapter(ArrayList<Model> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHoder holder, int position) {
        Model model=modelArrayList.get(position);
        holder.txtname.setText(model.getSname());
        holder.txtsub.setText(model.getSsubject());

        //icon background random color
        Random random=new Random();
        int color= Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(256));
        holder.icon.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder{
        private TextView txtname,txtsub;
        private ImageView icon;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtname=(TextView)itemView.findViewById(R.id.txtname);
            txtsub=(TextView)itemView.findViewById(R.id.txtsub);
            icon=(ImageView)itemView.findViewById(R.id.icon);
        }
    }
}