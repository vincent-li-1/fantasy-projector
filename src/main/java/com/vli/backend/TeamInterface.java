package com.vli.backend;

public interface TeamInterface {
    /**
     * Calculate a team coefficients for all stats
     * @return float[] representing[RYds, PYds, RTD, PTD, TO, REC]
     */
    public float[] calculateTeamCoefficients();

    /**
     * Get an opponents defensive stats
     * @param String opponent team code
     * @return int[][] representing [dRYds[], dPYds[], dRTD[], dPTD[], dTO[], dREC[]]
     */
    int[][] getOpponentDefensiveStats(String opponentCode);

    /**
     * Calculate the averages of an opponents defensive stats
     * @param int[][] matrix of opponents stats
     * @return int[] representing averages [dRYds, dPYds, dRTD, dPTD, dTO, dREC];
     */
    public float[] averageOpponentDefensiveStats(int[][] opponentStats);

    /**
     * Get the expected stats against the next opponent
     * @return float[] representing [eRYds, ePYds, eRTD, ePTD, eTO, eREC]
     */
    public float[] expectedStatsAgainstNextOpp();
}