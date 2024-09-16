package com.icesi.edu.co;

import com.icesi.edu.co.entity.Team;
import com.icesi.edu.co.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TeamDataLoader implements CommandLineRunner {

    private final TeamRepository teamRepository;

    public TeamDataLoader(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teamRepository.count() == 0) {
            Team team1 = new Team();
            team1.setName("Team A");
            team1.setDescription("Description for Team A");
            team1.setAmount(5);

            Team team2 = new Team();
            team2.setName("Team B");
            team2.setDescription("Description for Team B");
            team2.setAmount(10);

            Team team3 = new Team();
            team3.setName("Team C");
            team3.setDescription("Description for Team C");
            team3.setAmount(15);

            teamRepository.save(team1);
            teamRepository.save(team2);
            teamRepository.save(team3);

            System.out.println("DataLoader: Equipos cargados en la base de datos.");
        } else {
            System.out.println("DataLoader: Equipos ya existentes, no se cargaron nuevos datos.");
        }
    }
}
