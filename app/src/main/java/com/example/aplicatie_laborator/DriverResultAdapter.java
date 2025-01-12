package com.example.aplicatie_laborator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverResultAdapter extends RecyclerView.Adapter<DriverResultAdapter.DriverResultViewHolder> {
    private List<DriverResult> driverResults;

    public DriverResultAdapter(List<DriverResult> driverResults) {
        this.driverResults = driverResults;
    }

    @NonNull
    @Override
    public DriverResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver_result, parent, false);
        return new DriverResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverResultViewHolder holder, int position) {
        DriverResult result = driverResults.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return driverResults.size();
    }

    static class DriverResultViewHolder extends RecyclerView.ViewHolder {
        private TextView driverNameTextView;
        private TextView positionTextView;
        private TextView teamTextView;

        public DriverResultViewHolder(@NonNull View itemView) {
            super(itemView);
            driverNameTextView = itemView.findViewById(R.id.driver_name);
            positionTextView = itemView.findViewById(R.id.position);
            teamTextView = itemView.findViewById(R.id.team);
        }

        public void bind(DriverResult result) {
            driverNameTextView.setText(result.getDriverName());
            positionTextView.setText(result.getPosition());
            teamTextView.setText(result.getTeam());
        }
    }
}
