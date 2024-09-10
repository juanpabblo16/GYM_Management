package com.icesi.edu.co.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.icesi.edu.co.entity.Team;
import com.icesi.edu.co.service.TeamService;

@RestController
@RequestMapping("/api/gym/")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/team")
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public Team addTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }


    @GetMapping("/team")
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
}
