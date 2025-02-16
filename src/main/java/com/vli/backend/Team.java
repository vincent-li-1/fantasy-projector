package com.vli.backend;

import java.util.Arrays;
import java.util.List;

public class Team implements TeamInterface {

    /**
     * Stats are an array of arrays, where each indidual array
     * is the weekly stat:
     * [rushYds[], passYds[], rushTDs[], passTDs[], turnovers[], completions[]]
     */
    protected int[][] offensiveStats;
    protected int[][] defensiveStats;
    protected String[] schedule;
    public String code;
    public String teamName;
    public String nextOpp;
    private float[] coefficientWeights;

    public Team(
    int[][] offensiveStats,
    int[][] defensiveStats,
    String[] schedule,
    String code,
    String teamName,
    String nextOpp
    ) {
        this.offensiveStats = offensiveStats;
        this.defensiveStats = defensiveStats;
        this.schedule = schedule;
        this.code = code;

        this.teamName = teamName;
        this.coefficientWeights = new float[]{1f, 0.8f, 0.5f};
        this.nextOpp = nextOpp;
    }

    public void addWeek(int[] offensiveStatsToAdd, int[] defensiveStatsToAdd, String opp) {
        String[] updatedSchedule = new String[schedule.length + 1];
        for (int i = 0; i < schedule.length; i++) {
            updatedSchedule[i] = schedule[i];
        }
        updatedSchedule[schedule.length] = opp;
        schedule = updatedSchedule;
        for (int i = 0; i < offensiveStats.length; i++) {
            offensiveStats[i] = addIntToArray(offensiveStats[i], offensiveStatsToAdd[i]);
            defensiveStats[i] = addIntToArray(defensiveStats[i], defensiveStatsToAdd[i]);
        }
    }

    private int[] addIntToArray(int[] array, int value) {
        int[] updatedArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            updatedArray[i] = array[i];
        }
        updatedArray[array.length] = value;
        return updatedArray;
    }

    public float[] calculateTeamCoefficients() {
        float[] coefficients = new float[6];
        Arrays.fill(coefficients, 0f);
        float divisor = 0;
        int end = Math.max(schedule.length - coefficientWeights.length, 0);
        for (int i = schedule.length - 1; i >= end; i--) {
            float[] averages = averageOpponentDefensiveStats(getOpponentDefensiveStats(schedule[i]));
            for (int j = 0; j < offensiveStats.length; j++) {
                if (averages[j] != 0) {
                    coefficients[j] += ((float) offensiveStats[j][i]/averages[j]) * coefficientWeights[schedule.length - 1 - i];
                }
            }
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
        return opp.defensiveStats;
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

    public float[] expectedStatsAgainstNextOpp() {
        float[] coefficients = calculateTeamCoefficients();
        float[] nextOppDefAverages = averageOpponentDefensiveStats(getOpponentDefensiveStats(nextOpp));
        float[] expectedStats = new float[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            expectedStats[i] = coefficients[i] * nextOppDefAverages[i];
        }
        return expectedStats;
    };
}