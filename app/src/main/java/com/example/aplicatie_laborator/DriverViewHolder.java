package com.example.aplicatie_laborator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DriverViewHolder extends RecyclerView.ViewHolder {

    public ImageView driverImage;
    public TextView driverName;
    public TextView driverDescription;

    public DriverViewHolder(@NonNull View itemView) {
        super(itemView);

        driverImage = itemView.findViewById(R.id.driver_image);
        driverName = itemView.findViewById(R.id.driver_name);
        driverDescription = itemView.findViewById(R.id.driver_description);
    }
}
