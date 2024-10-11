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
    float[] coefficientWeights;

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
        this.coefficientWeights = new float[]{1f, 0.9f, 0.75f, 0.5f, 0.2f};
    }

    public float[] calculateTeamCoefficients() {
        float[] coefficients = new float[6];
        Arrays.fill(coefficients, 0f);
        float divisor = 0;
        int end = Math.max(schedule.length - 5, 0);
        for (int i = schedule.length - 1; i >= end; i--) {
            float[] averages = averageOpponentDefensiveStats(getOpponentDefensiveStats(schedule[i]));
            System.out.println(averages[0]);
            float oRSC = ((float) oRS[i])/averages[0];
            float oPSC = ((float) oPS[i])/averages[1];
            float oRTDC = ((float) oRTD[i])/averages[2];
            float oPTDC = ((float) oPTD[i])/averages[3];
            float oTOC = ((float) oTO[i])/averages[4];
            float oRECC = ((float) oREC[i])/averages[5];
            coefficients[0] += oRSC * coefficientWeights[schedule.length - 1 - i];
            coefficients[1] += oPSC * coefficientWeights[schedule.length - 1 - i];
            coefficients[2] += oRTDC * coefficientWeights[schedule.length - 1 - i];
            coefficients[3] += oPTDC * coefficientWeights[schedule.length - 1 - i];
            coefficients[4] += oTOC * coefficientWeights[schedule.length - 1 - i];
            coefficients[5] += oRECC * coefficientWeights[schedule.length - 1 - i];
            divisor += coefficientWeights[schedule.length - 1 -i];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] /= divisor;
        }
        return coefficients;
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
        Arrays.fill(averages, 0f);
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