package com.vli.backend;

import java.util.Arrays;

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
        TeamDatabase database = TeamDatabase.getInstance();
        Team opp = database.getTeam(opponentCode);
        int[][] stats = new int[6][opp.schedule.length];
        stats[0] = opp.dRS;
        stats[1] = opp.dPS;
        stats[2] = opp.dRTD;
        stats[3] = opp.dPTD;
        stats[4] = opp.dTO;
        stats[5] = opp.dREC;
        return stats;
    }

    public float[] averageOpponentDefensiveStats(int[][] opponentStats) {
        float[] averages = new float[6];
        Arrays.fill(averages, 0.0f);
        for (int i = 0; i < opponentStats[0].length; i++) {
            for (int j = 0; j < averages.length; j++) {
                averages[j] += (float) opponentStats[j][i];
            }
        }
        for (int i = 0; i < averages.length; i++) {
            averages[i] /= opponentStats[0].length;
        }
        return averages;
    }
}