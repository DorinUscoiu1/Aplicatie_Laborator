package com.example.aplicatie_laborator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicatie_laborator.R;
import com.example.aplicatie_laborator.Team;
import java.util.List;
public class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder> {

    private List<Team> teamList;
    private OnTeamClickListener listener;

    public TeamAdapter(List<Team> teamList, OnTeamClickListener listener) {
        this.teamList = teamList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.teamName.setText(team.getName());
        holder.teamDescription.setText(team.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onTeamClick(team));
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public interface OnTeamClickListener {
        void onTeamClick(Team team);
    }
}
