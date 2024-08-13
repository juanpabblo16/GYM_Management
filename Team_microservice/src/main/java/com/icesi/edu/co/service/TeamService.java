package com.icesi.edu.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.co.entity.Team;
import com.icesi.edu.co.repository.TeamRepository;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }


}
