package com.vli.backend;

public interface TeamInterface {
    // int[] offRushingStats;
    // int[] offPassingStats;
    // int[] offRushingTDs;
    // int[] offPassingTDs;
    // int[] offTurnovers;
    // int[] offReceptions;
    // int[] defRushingStats;
    // int[] defPassingStats;
    // int[] defRushingTDs;
    // int[] defPassingTDs;
    // int[] defTurnovers;
    // int[] defReceptions;
    // String[] schedule;
    // String code
    // String location
    // String teamName

    /**
     * Calculate a team coefficient for a given stat
     * @param String stat to calculate
     * @return float coefficient value
     */
    public float calculateTeamCoefficient(String stat);

    /**
     * Get an opponents defensive stats
     * @param String opponent team code
     * @return int[][] representing [dRS[], dPS[], dRTD[], dPTD[], dTO[], dREC[]]
     */
    public int[][] getOpponentDefensiveStats(String opponentCode);

    /**
     * Calculate the averages of an opponents defensive stats
     * @param int[][] matrix of opponents stats
     * @return int[] representing averages [dRS, dPS, dRTD, dPTD, dTO, dREC];
     */
    public int[] averageOpponentDefensiveStats(int[][] opponentStats);
}