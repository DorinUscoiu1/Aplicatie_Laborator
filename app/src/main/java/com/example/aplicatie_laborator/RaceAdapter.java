package com.example.aplicatie_laborator;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicatie_laborator.R;
import com.example.aplicatie_laborator.Race;

import java.util.List;

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.RaceViewHolder> {
    private List<Race> races;
    private OnRaceClickListener listener;

    private static MediaPlayer mediaPlayer;
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

        holder.playAnthemButton.setOnClickListener(v -> {
            String winner = getWinner(race);
            if (winner != null) {
                playNationalAnthem(holder.itemView.getContext(), winner);
            }
        });

        holder.stopAnthemButton.setOnClickListener(v -> stopNationalAnthem());
    }

    private String getWinner(Race race) {
        if (race.getResults() != null && !race.getResults().isEmpty()) {
            DriverResult winner = race.getResults().get(0); // Primul loc
            if (isTargetDriver(winner.getDriverName())) {
                return winner.getDriverName();
            }
        }
        return null;
    }

    private boolean isTargetDriver(String driverName) {
        return driverName.equals("Max Verstappen") ||
                driverName.equals("Lando Norris") ||
                driverName.equals("Oscar Piastri") ||
                driverName.equals("Charles Leclerc") ||
                driverName.equals("Carlos Sainz") ||
                driverName.equals("George Russell") ||
                driverName.equals("Lewis Hamilton");
    }

    private void playNationalAnthem(Context context, String driverName) {
        int anthemResId;
        stopNationalAnthem();
        switch (driverName) {

            case "Max Verstappen":
                anthemResId = R.raw.netherlands_anthem;
                break;
            case "Lando Norris":
                anthemResId = R.raw.uk_anthem;
                break;
            case "George Russell":
                anthemResId = R.raw.uk_anthem;
                break;
            case "Lewis Hamilton":
                anthemResId = R.raw.uk_anthem;
                break;
            case "Oscar Piastri":
                anthemResId = R.raw.australia_anthem;
                break;
            case "Charles Leclerc":
                anthemResId = R.raw.monaco_anthem;
                break;
            case "Carlos Sainz":
                anthemResId = R.raw.spain_anthem;
                break;
            default:
                return;
        }
        mediaPlayer = MediaPlayer.create(context, anthemResId);
        mediaPlayer.start();
    }
    private void stopNationalAnthem() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public int getItemCount() {
        return races.size();
    }

    static class RaceViewHolder extends RecyclerView.ViewHolder {
        private TextView raceNameTextView;
        private TextView raceDateTextView;
        private TextView circuitNameTextView;
        private Button stopAnthemButton;

        private Button playAnthemButton;

        public RaceViewHolder(@NonNull View itemView) {
            super(itemView);
            raceNameTextView = itemView.findViewById(R.id.race_name);
            raceDateTextView = itemView.findViewById(R.id.race_date);
            circuitNameTextView = itemView.findViewById(R.id.race_circuit);

            playAnthemButton = itemView.findViewById(R.id.play_anthem_button);

            stopAnthemButton = itemView.findViewById(R.id.stop_anthem_button);
        }

        public void bind(final Race race, final OnRaceClickListener listener) {
            raceNameTextView.setText(race.getRaceName());
            raceDateTextView.setText(race.getDate());
            circuitNameTextView.setText(race.getCircuitName());
            itemView.setOnClickListener(v -> listener.onRaceClick(race));
        }
    }
}
