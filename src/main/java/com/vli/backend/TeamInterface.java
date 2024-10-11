package com.vli.backend;

public interface TeamInterface {
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
    int[][] getOpponentDefensiveStats(String opponentCode);

    /**
     * Calculate the averages of an opponents defensive stats
     * @param int[][] matrix of opponents stats
     * @return int[] representing averages [dRS, dPS, dRTD, dPTD, dTO, dREC];
     */
    public float[] averageOpponentDefensiveStats(int[][] opponentStats);
}