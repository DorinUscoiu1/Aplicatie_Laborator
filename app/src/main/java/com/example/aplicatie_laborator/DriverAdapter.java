package com.example.aplicatie_laborator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicatie_laborator.R;
import com.example.aplicatie_laborator.Driver;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverViewHolder> {

    private final List<Driver> driverList;
    private final OnDriverClickListener listener;

    public DriverAdapter(List<Driver> driverList, OnDriverClickListener listener) {
        this.driverList = driverList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.driverName.setText(driver.getName());
        holder.driverDescription.setText(driver.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onDriverClick(driver));
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public interface OnDriverClickListener {
        void onDriverClick(Driver driver);
    }
}
