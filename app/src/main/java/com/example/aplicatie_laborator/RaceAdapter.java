package com.example.aplicatie_laborator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicatie_laborator.R;
import com.example.aplicatie_laborator.Race;

import java.util.List;

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.RaceViewHolder> {
    private List<Race> races;
    private OnRaceClickListener listener;

    public interface OnRaceClickListener {
        void onRaceClick(Race race);
    }

    public RaceAdapter(List<Race> races, OnRaceClickListener listener) {
        this.races = races;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_race, parent, false);
        return new RaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaceViewHolder holder, int position) {
        Race race = races.get(position);
        holder.bind(race, listener);
    }

    @Override
    public int getItemCount() {
        return races.size();
    }

    static class RaceViewHolder extends RecyclerView.ViewHolder {
        private TextView raceNameTextView;
        private TextView raceDateTextView;
        private TextView circuitNameTextView;

        public RaceViewHolder(@NonNull View itemView) {
            super(itemView);
            raceNameTextView = itemView.findViewById(R.id.race_name);
            raceDateTextView = itemView.findViewById(R.id.race_date);
            circuitNameTextView = itemView.findViewById(R.id.race_circuit);
        }

        public void bind(final Race race, final OnRaceClickListener listener) {
            raceNameTextView.setText(race.getRaceName());
            raceDateTextView.setText(race.getDate());
            circuitNameTextView.setText(race.getCircuitName());
            itemView.setOnClickListener(v -> listener.onRaceClick(race));
        }
    }
}
