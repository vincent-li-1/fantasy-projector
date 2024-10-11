package com.vli.backend;

public class Team implements TeamInterface {

    int[] oRS;
    int[] oPS;
    int[] oRTD;
    int[] oPTD;
    int[] oTO;
    int[] oREC;
    int[] dRS;
    int[] dPS;
    int[] dRTD;
    int[] dPTD;
    int[] dTO;
    int[] dREC;
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
        this.oRS = offRushingStats;
        this.oPS = offPassingStats;
        this.oRTD = offRushingTDs;
        this.oPTD = offPassingTDs;
        this.oTO = offTurnovers;
        this.oREC = offReceptions;
        this.dRS = defRushingStats;
        this.dPS = defPassingStats;
        this.dRTD = defRushingTDs;
        this.dPTD = defPassingTDs;
        this.dTO = defTurnovers;
        this.dREC = defReceptions;
        this.schedule = schedule;
        this.code = code;
        this.location = location;
        this.teamName = teamName;
    }

    public float calculateTeamCoefficient(String stat) {
        return 0.0f;
    }

    public int[][] getOpponentDefensiveStats(String opponentCode) {
        return new int[6][3];
    }
}