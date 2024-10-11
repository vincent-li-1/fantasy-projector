package com.vli.backend;

import java.util.HashMap;

public interface CalculatorInterface{
    // private HashMap<String, String[]> teamSchedules;
    // private HashMap<String, Integer[]> teamPassingStats;
    // private Hashmap<String, Integer[]> teamRushingStats;

    /**
     * Calculate the average coefficient for a given team i.e. on average, what percent of the opposing team's passing yards allowed does this team record
     * @param String team name and statistic to calculate coefficient for
     * @return float coefficient
     */
    public float calculateTeamCoefficient(String team, String stat);

    /**
     * Calculate the expected percentage of their team's stats a player can have, weighted towards recent results
     * @param String player name and stat
     * @return float coefficient
     */
    public float calculatePlayerCoefficient(String player, String stat);

    /**
     * Calculate a player's projection
     * @param String player name
     * @return float projected points
     */
    public float calculateProjection(String player);
    
}