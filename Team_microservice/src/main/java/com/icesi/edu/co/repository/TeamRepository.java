package com.icesi.edu.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.edu.co.entity.Team;


public interface TeamRepository extends JpaRepository<Team, Long> {
}