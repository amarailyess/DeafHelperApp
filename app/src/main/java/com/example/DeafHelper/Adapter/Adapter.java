package com.example.DeafHelper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.DeafHelper.Activity.ServiceActivity;
import com.example.DeafHelper.Activity.TermActivity;
import com.example.DeafHelper.Domain.Term;
import com.example.DeafHelper.Domain.Service;
import com.example.DeafHelper.Helper.ImageProcessing;
import com.example.DeafHelper.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    private ArrayList<Term> terms;
    private ArrayList<Service> services;
    private ArrayList<String> list;
    String type;
    Context context;
    public Adapter(ArrayList items, String type, Context context) {
        this.type = type;
        this.context = context;
        if (type.equals("service")){
            this.services = items;
        }else{
            this.terms = items;
        }
        list = new ArrayList<String>();
        list.add("term_image");list.add("term_image");list.add("term_image");list.add("term_image");
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item,parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        if (type.equals("service")){
            holder.title.setText(services.get(position).getLabel());
            System.out.println("********* "+services.get(position).getImage());
            new ImageProcessing(holder.imageView).execute(services.get(position).getImage());
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(context, ServiceActivity.class);
                        intent.putExtra("label", services.get(position).getLabel());
                        intent.putExtra("video", services.get(position).getVideo());
                        context.startActivity(intent);
                }
            });
        }else{
            holder.title.setText(terms.get(position).getLabel());
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(terms.get(position).getImage(), "drawable", holder.itemView.getContext().getPackageName());
            Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.imageView);
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TermActivity.class);
                    intent.putExtra("id", terms.get(position).getId());
                    intent.putExtra("label", terms.get(position).getLabel());
                    intent.putExtra("description", terms.get(position).getDescription());
                    intent.putStringArrayListExtra("list",list);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("service")){
            return services.size();
        }else{
            return terms.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            imageView = itemView.findViewById(R.id.picItem);
            mainLayout = itemView.findViewById(R.id.itemMainLayout);

        }
    }
}
