package com.example.aplicatie_laborator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamViewHolder extends RecyclerView.ViewHolder {

    public TextView teamName;
    public TextView teamDescription;
    public ImageView teamLogo;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        teamName = itemView.findViewById(R.id.team_name);
        teamDescription = itemView.findViewById(R.id.team_description);
        teamLogo=itemView.findViewById(R.id.team_logo);
    }
}
