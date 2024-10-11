package com.vli.backend;

import java.util.Arrays;

public class Player implements PlayerInterface{

    /**
     * Stats are structured as [RushYds[], PassYds[], RecYds[], RushTDs[], PassTDs[], RecTDs[], TO[], Rec[]]
     */
    int[][] stats;
    Team team;
    String name;
    String position;
    float[] coefficientWeights;

    public Player(int[][] stats, Team team, String name, String position) {
        this.stats = stats;
        this.team = team;
        this.name = name;
        this.position = position;
        this.coefficientWeights = new float[]{1f, 0.8f, 0.5f};
    }

    public int[][] getTeamStatsWithReceiving() {
        int[][] teamStats = team.offensiveStats;
        int[][] teamStatsWithRec = new int[][]{teamStats[0], teamStats[1], teamStats[1], teamStats[2], teamStats[3], teamStats[3], teamStats[4], teamStats[5]};
        return teamStatsWithRec;
    };

    // Calculate what percentage of the teams stats a player is expected to get
    public float[] calculateWeightedTeamStatCoefficients() {
        int[][] teamStats = this.getTeamStatsWithReceiving();
        float[] coefficients = new float[stats.length];
        Arrays.fill(coefficients, 0f);
        float divisor = 0;
        int end = Math.max(stats[0].length - coefficientWeights.length, 0);
        for (int i = stats[0].length - 1; i >= end; i--) {
            for (int j = 0; j < stats.length; j++) {
                if (teamStats[j][i] != 0) {
                    coefficients[j] += ((float) stats[j][i]/teamStats[j][i]) * coefficientWeights[stats[0].length - 1 - i];
                }
            }
            divisor += coefficientWeights[stats[0].length - 1 - i];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] /= divisor;
        }
        return coefficients;
    };

    public float[] projectStatsAgainstNextOpp() {
        float[] projectedTeamStats = team.expectedStatsAgainstNextOpp();
        float[] projectedTeamStatsWithRec = new float[]{projectedTeamStats[0], projectedTeamStats[1], projectedTeamStats[1], projectedTeamStats[2], projectedTeamStats[3], projectedTeamStats[3], projectedTeamStats[4], projectedTeamStats[5]};
        float[] coefficients = calculateWeightedTeamStatCoefficients();
        float[] projectedPlayerStats = new float[projectedTeamStatsWithRec.length];
        for (int i = 0; i < projectedPlayerStats.length; i++) {
            projectedPlayerStats[i] = coefficients[i] * projectedTeamStatsWithRec[i];
        }
        return projectedPlayerStats;
    };

    public float projectPointsAgainstNextOpp(float pointsPerRec) {
        if (team.nextOpp == "BYE") {
            return 0;
        }
        float[] statsProj = projectStatsAgainstNextOpp();
        float[] multiplier = new float[]{0.1f, 0.04f, 0.1f, 6f, 4f, 6f, -2f, pointsPerRec};
        float pts = 0;
        for (int i = 0; i < statsProj.length; i++) {
            pts += statsProj[i] * multiplier[i];
        }
        return pts;
    };

}