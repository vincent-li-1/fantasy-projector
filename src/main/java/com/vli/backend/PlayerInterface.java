package com.vli.backend;

public interface PlayerInterface {

    public int[][] getTeamStatsWithReceiving();

    // Calculate what percentage of the teams stats a player is expected to get
    public float[] calculateWeightedTeamStatCoefficients();

    public float[] projectStatsAgainstNextOpp();

    public float projectPointsAgainstNextOpp(float pointsPerRec);

}