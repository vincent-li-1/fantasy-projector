package com.vli.backend;

public interface TeamInterface {
    /**
     * Calculate a team coefficients for all stats
     * @param String stat to calculate
     * @return float[] representing[RS, PS, RTD, PTD, TO, REC]
     */
    public float[] calculateTeamCoefficients();

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

    /**
     * Get the expected stats against the next opponent
     * @param String opponent team code
     * @return float[] representing [eRS, ePS, eRTD, ePTD, eTO, eREC]
     */
}