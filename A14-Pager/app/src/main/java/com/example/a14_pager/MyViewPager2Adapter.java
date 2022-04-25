package com.example.a14_pager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyViewPager2Adapter extends RecyclerView.Adapter<MyViewPager2Adapter.MyViewHolder> {
    private ArrayList<MyRecyclerViewData> data;
    MyViewPager2Adapter(ArrayList<MyRecyclerViewData> data1) {
        this.data = data1;
    }

    // This method returns our layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main, parent, false);
        return new MyViewHolder(view);

    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyRecyclerViewData model = data.get(position);
        holder.conditions.setText(model.getConditions());
        holder.temp.setText(model.getTemperature() + "\u00B0");
        holder.city.setText(model.getName());
        holder.low.setText("L " + model.getMinTemp());
        holder.high.setText("H " + model.getMaxTemp());

        if ( model.getClimaCode() >= 200 &&  model.getClimaCode() < 234){
            holder.climateIcon.setText("F");
        }
        // Drizzle
        else if ( model.getClimaCode() >= 300 &&  model.getClimaCode() < 322){
            holder.climateIcon.setText("6");
        }
        // Rain
        else if ( model.getClimaCode() >= 500 &&  model.getClimaCode() < 532){
            holder.climateIcon.setText("*");
        }
        //Snow
        else if ( model.getClimaCode() >= 600 &&  model.getClimaCode() < 623){
            holder.climateIcon.setText(";");
        }
        // Clouds
        else if ( model.getClimaCode() >= 801 &&  model.getClimaCode() < 805){
            holder.climateIcon.setText("!");
        }
        // Clear
        else{
            holder.climateIcon.setText("I");
        }


        // Set Background based on current temperature
        if (model.getTemperature() < 0 || model.getTemperature() > 0 && model.getTemperature() <= 50){
            holder.constraintlayout.setBackgroundColor(Color.parseColor("#6BCBF8"));
        }else if (model.getTemperature() > 50 && model.getTemperature() <= 75){
            holder.constraintlayout.setBackgroundColor(Color.parseColor("#07A6F1"));
        }
        else {
            holder.constraintlayout.setBackgroundColor(Color.parseColor("#49C5FF"));
        }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    // The ViewHolder class holds the view
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView climateIcon, conditions,city,temp,high,low;
        ConstraintLayout constraintlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            climateIcon = itemView.findViewById(R.id.climateIcon);
            conditions = itemView.findViewById(R.id.conditions);
            city = itemView.findViewById(R.id.city);
            temp = itemView.findViewById(R.id.temp);
            high = itemView.findViewById(R.id.tempMax);
            low = itemView.findViewById(R.id.tempMin);
            constraintlayout = itemView.findViewById(R.id.constraintLayout);


        }
    }
}
