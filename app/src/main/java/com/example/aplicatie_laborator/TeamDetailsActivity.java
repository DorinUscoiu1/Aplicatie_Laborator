package com.example.aplicatie_laborator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicatie_laborator.DriverAdapter;
import com.example.aplicatie_laborator.Driver;
import com.example.aplicatie_laborator.Team;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        Team team = (Team) getIntent().getSerializableExtra("team");
        TextView teamNameTextView = findViewById(R.id.team_name);
        TextView teamDescriptionTextView = findViewById(R.id.team_description);
        teamNameTextView.setText(team.getName());
        teamDescriptionTextView.setText(team.getNationality());
        List<Driver> driverList = getDriversForTeam(team);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DriverAdapter adapter = new DriverAdapter(driverList, driver -> {
            Intent intent = new Intent(TeamDetailsActivity.this, DriverDetailsActivity.class);
            intent.putExtra("driver", driver);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
    private List<Driver> getDriversForTeam(Team team) {
        List<Driver> driverList = new ArrayList<>();
        switch (team.getName()) {
            case "Mercedes":
                driverList.add(new Driver("Lewis Hamilton", "Sir Lewis Carl Davidson Hamilton MBE este un pilot britanic de Formula 1 ce concurează în prezent pentru Ferrari, pilotând anterior pentru McLaren din 2007 până în 2012, și pentru Mercedes din 2013 până în 2024.", R.drawable.lewis_hamilton));
                driverList.add(new Driver("George Russell", "George William Russell este un pilot de curse britanic ce concurează în Campionatul Mondial de Formula 1 pentru constructorul german Mercedes. El a fost Campionul de Formula 2 din 2018 pentru echipa ART și Campionul Seriei GP3 din 2017.", R.drawable.george_russell));
                break;
            case "Ferrari":
                driverList.add(new Driver("Charles Leclerc", "Charles Marc Hervé Perceval Leclerc este un pilot de curse monegasc ce concurează în prezent în Formula 1 pentru echipa Ferrari. El a câștigat campionatul de curse GP3 în 2016 și Campionatul de Formula 2 în 2017.", R.drawable.charles_leclerc));
                driverList.add(new Driver("Carlos Sainz", "Carlos Sainz Vázquez de Castro, cunoscut și sub numele de Carlos Sainz Jr., sau simplu Carlos Sainz, este un pilot de curse spaniol care concurează în Campionatul Mondial de Formula 1 pentru Williams. Este fiul lui Carlos Sainz, dublu campion mondial la raliuri, și nepotul pilotului de raliu Antonio Sainz.", R.drawable.carlos_sainz));
                break;
            case "Red Bull":
                driverList.add(new Driver("Max Verstappen", "Max Emilian Verstappen este un pilot de curse auto belgiano-neerlandez care concurează în prezent pentru echipa Red Bull Racing în Campionatul Mondial de Formula 1. Este cvadruplu campion mondial în această competiție, după ce a cucerit titlurile din 2021, 2022, 2023 și 2024.", R.drawable.max_verstappen));
                driverList.add(new Driver("Sergio Perez", "Sergio Michel Pérez Mendoza, poreclit „Checo”, este un pilot de curse mexican ce a concurat ultima oară în Campionatul Mondial de Formula 1 din 2024 pentru echipa Red Bull Racing. ", R.drawable.sergio_perez));
                break;
            case "Alpine F1 Team":
                driverList.add(new Driver("Esteban Ocon", "Esteban José Jean-Pierre Ocon-Khelfane este un pilot de curse francez care concurează în Campionatul Mondial de Formula 1 pentru echipa Haas. El și-a făcut debutul în Formula 1 pentru Manor Racing la Marele Premiu al Belgiei din 2016, înlocuindu-l pe Rio Haryanto.", R.drawable.esteban_ocon));
                driverList.add(new Driver("Pierre Gasly", "Pierre Gasly este un pilot de curse francez care concurează în prezent în Campionatul Mondial de Formula 1 pentru echipa Alpine F1 Team. Gasly a ieșit campionul Seriei GP2 din 2016 și a terminat pe locul 2 în Formula Renault 3.5 Seria 2014 și Campionatul Super Formula 2017.", R.drawable.pierre_gasly));
                break;
            case "McLaren":
                driverList.add(new Driver("Lando Norris", "Lando Norris este un pilot de curse britanic care concurează în Formula 1 pentru echipa McLaren. Norris a fost vicecampion mondial al piloților în Formula 1 în 2024, și a câștigat patru Mari Premii pe parcursul a șase sezoane.", R.drawable.lando_norris));
                driverList.add(new Driver("Oscar Piastri", "Oscar Jack Piastri este un pilot de curse australian care concurează în prezent în Formula 1 pentru McLaren. Piastri a câștigat Formula Renault Eurocup 2019 cu R-ace GP și a câștigat Campionatul FIA de Formula 3 2020 și Campionatul de Formula 2 2021 cu Prema Racing.", R.drawable.oscar_piastri));
                break;
            case "Aston Martin":
                driverList.add(new Driver("Fernando Alonso", "Fernando Alonso Díaz este un pilot de curse spaniol care concurează în prezent în Formula 1 pentru echipa Aston Martin Aramco. El a câștigat Campionatul Mondial de Formula 1 la piloți în 2005 și 2006 cu echipa Renault. Alonso a condus pe parcursul carieriei și pentru Minardi, McLaren, Ferrari și Alpine.", R.drawable.fernando_alonso));
                driverList.add(new Driver("Lance Stroll", "Lance Strulovitch, cunoscut mai bine sub numele de Lance Stroll, este un pilot de curse canadian ce concurează în Campionatul Mondial de Formula 1 pentru echipa Aston Martin. El a mai condus în trecut pentru Williams și Racing Point.", R.drawable.lance_stroll));
                break;
            case "Sauber":
                driverList.add(new Driver("Valtteri Bottas", "Valtteri Viktor Bottas este un pilot de curse finlandez ce a concurat ultima oară în 2024 în Campionatul Mondial de Formula 1 pentru echipa Kick Sauber. Între 2010 și 2012, Valtteri a fost pilot de teste pentru Williams, iar în sezonul 2013 a primit un loc în Formula 1, înlocuindu-l pe Bruno Senna.", R.drawable.valtteri_bottas));
                driverList.add(new Driver("Zhou Guanyu", "Zhou Guanyu este un pilot de curse chinez care a concurat ultima oară în 2024 în Campionatul Mondial de Formula 1 pentru echipa Kick Sauber. Este primul pilot chinez care a concurat vreodată în acest sport.", R.drawable.zhou_guanyu));
                break;
            case "Haas F1 Team":
                driverList.add(new Driver("Kevin Magnussen", "Kevin Jan Magnussen este un pilot de curse danez ce a concurat ultima oară în 2024 în Campionatul Mondial de Formula 1 pentru echipa Haas. El este fiul fostului pilot de curse Jan Magnussen.", R.drawable.kevin_magnussen));
                driverList.add(new Driver("Nico Hulkenberg", "Nicolas Hülkenberg, cunoscut mai bine sub numele de Nico Hülkenberg, este un pilot profesionist de curse german ce concurează în Campionatul Mondial de Formula 1 pentru echipa Kick Sauber", R.drawable.nico_hulkenberg));
                break;
            case "RB F1 Team":
                driverList.add(new Driver("Yuki Tsunoda", "Yuki Tsunoda este un pilot japonez de curse ce concurează în prezent în Formula 1 pentru echipa RB F1 Team. Sprijinit de Honda din 2016 prin Proiectul Formula Dream Honda, el a fost campionul japonez de F4 în 2018, iar în 2019 a primit sprijin și de la Red Bull.", R.drawable.yuki_tsunoda));
                driverList.add(new Driver("Daniel Ricciardo", "Daniel Joseph Ricciardo este un pilot de curse australian ce a concurat ultima dată în Campionatul Mondial de Formula 1 pentru echipa RB F1 Team. A debutat la Marele Premiu al Marii Britanii din 2011 cu echipa HRT.", R.drawable.daniel_ricciardo));
                break;
            case "Williams":
                driverList.add(new Driver("Alexander Albon", "Alexander Albon Ansusinha este un pilot de curse thailandez ce concurează în Formula 1 pentru echipa Williams. El și-a făcut debutul la Marele Premiu al Australiei din 2019, după ce a fost adus în echipa Scuderia Toro Rosso.", R.drawable.alex_albon));
                driverList.add(new Driver("Logan Sargeant", "Logan Hunter Sargeant este un pilot de curse auto american care a concurat ultima oară în Campionatul Mondial de Formula 1 din 2024 pentru Williams. ", R.drawable.logan_sargeant));
                break;
            default:
                //driverList.add(new Driver("Unknown Driver", "Details about Unknown Driver", R.drawable.placeholder_driver));
                break;
        }

        return driverList;
    }
}
