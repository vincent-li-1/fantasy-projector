package com.vli.backend;

public class Team implements TeamInterface {

    int[] offRushingStats;
    int[] offPassingStats;
    int[] offRushingTDs;
    int[] offPassingTDs;
    int[] offTurnovers;
    int[] offReceptions;
    int[] defRushingStats;
    int[] defPassingStats;
    int[] defRushingTDs;
    int[] defPassingTDs;
    int[] defTurnovers;
    int[] defReceptions;
    String[] schedule;
    String code;
    String location;
    String teamName;

    public Team(
    int[] offRushingStats,
    int[] offPassingStats,
    int[] offRushingTDs,
    int[] offPassingTDs,
    int[] offTurnovers,
    int[] offReceptions,
    int[] defRushingStats,
    int[] defPassingStats,
    int[] defRushingTDs,
    int[] defPassingTDs,
    int[] defTurnovers,
    int[] defReceptions,
    String[] schedule,
    String code,
    String location,
    String teamName
    ) {
        this.offRushingStats = offRushingStats;
        this.offPassingStats = offPassingStats;
        this.offRushingTDs = offRushingTDs;
        this.offPassingTDs = offPassingTDs;
        this.offTurnovers = offTurnovers;
        this.offReceptions = offReceptions;
        this.defRushingStats = defRushingStats;
        this.defRushingTDs = defRushingTDs;
        this.defPassingTDs = defPassingTDs;
        this.defTurnovers = defTurnovers;
        this.defReceptions = defReceptions;
        this.schedule = schedule;
        this.code = code;
        this.location = location;
        this.teamName = teamName;
    }

    public float calculateTeamCoefficient(String team, String stat) {
        return 0.0f;
    }
}