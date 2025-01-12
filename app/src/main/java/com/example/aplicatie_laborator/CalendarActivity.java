package com.example.aplicatie_laborator;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://ergast.com/api/f1/";
    private static final String RACE_FILE_PREFIX = "race_";
    private static final String RACE_CALENDAR_FILE = "race_calendar.json";
    private List<Race> raceList = new ArrayList<>();
    private RaceAdapter raceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_calendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        raceAdapter = new RaceAdapter(raceList, race -> {
            Intent intent = new Intent(CalendarActivity.this, RaceDetailsActivity.class);
            intent.putExtra("race", race);
            startActivity(intent);
        });
        recyclerView.setAdapter(raceAdapter);

        fetchRaceCalendar();
    }

    private void fetchRaceCalendar() {
        String cachedData = readFromFile(RACE_CALENDAR_FILE);
        if (cachedData != null && !cachedData.isEmpty()) {
            handleJson(cachedData);
        } else {
            String url = BASE_URL + "2024.json";
            new FetchRaceTask().execute(url);
        }
    }

    private void handleJson(String jsonString) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonObject raceTable = jsonObject.getAsJsonObject("MRData").getAsJsonObject("RaceTable");
            JsonArray races = raceTable.getAsJsonArray("Races");

            for (int i = 0; i < races.size(); i++) {
                JsonObject race = races.get(i).getAsJsonObject();
                String raceName = race.get("raceName").getAsString();
                String date = race.get("date").getAsString();
                String circuitName = race.getAsJsonObject("Circuit").get("circuitName").getAsString();

                Race raceObj = new Race(raceName, date, circuitName, null);
                String round = race.get("round").getAsString();
                String raceFileName = RACE_FILE_PREFIX + round + ".json";

                String cachedRaceData = readFromFile(raceFileName);
                if (cachedRaceData != null && !cachedRaceData.isEmpty()) {
                    parseRaceResults(cachedRaceData, raceObj);
                } else {
                    String raceUrl = BASE_URL + "2024/" + round + "/results.json";
                    fetchRaceResults(raceUrl, raceFileName, raceObj);
                }

                raceList.add(raceObj);
            }
            raceAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchRaceResults(String raceUrl, String raceFileName, Race race) {
        new FetchResultsTask(raceFileName, race).execute(raceUrl);
    }

    private void saveToFile(String fileName, String data) {
        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try (FileInputStream fis = openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result.toString();
    }

    private class FetchRaceTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    result.append((char) data);
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                saveToFile(RACE_CALENDAR_FILE, result);
                handleJson(result);
            }
        }
    }

    private class FetchResultsTask extends AsyncTask<String, Void, String> {
        private String raceFileName;
        private Race race;

        public FetchResultsTask(String raceFileName, Race race) {
            this.raceFileName = raceFileName;
            this.race = race;
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                int data = reader.read();
                while (data != -1) {
                    result.append((char) data);
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                saveToFile(raceFileName, result);
                parseRaceResults(result, race);
            }
        }
    }

    private void parseRaceResults(String jsonString, Race race) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonArray resultsArray = jsonObject.getAsJsonObject("MRData").getAsJsonObject("RaceTable")
                    .getAsJsonArray("Races").get(0).getAsJsonObject().getAsJsonArray("Results");

            List<DriverResult> results = new ArrayList<>();
            for (int i = 0; i < resultsArray.size(); i++) {
                JsonObject result = resultsArray.get(i).getAsJsonObject();
                String driverName = result.getAsJsonObject("Driver").get("givenName").getAsString() + " " +
                        result.getAsJsonObject("Driver").get("familyName").getAsString();
                String position = result.get("position").getAsString();
                String team = result.getAsJsonObject("Constructor").get("name").getAsString();

                results.add(new DriverResult(driverName, position, team));
            }

            race.setResults(results);
            raceAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
