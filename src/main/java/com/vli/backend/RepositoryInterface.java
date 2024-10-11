package com.vli.backend;

import java.util.HashMap;

public interface RepositoryInterface {

    public HashMap<String, String[]> fetchTeamOpponents(String team);

    public HashMap<String, Integer[]> fetchAllTeamRushingStats();

    public HashMap<String, Integer[]> fetchAllTeamPassingStats();

    public HashMap<String, Integer[]> fetchAllTeamPassingTDs();

    public HashMap<String, Integer[]> fetchAllTeamRushingTDs();

    public HashMap<String, Integer[]> fetchAllTeamReceptions();

    public HashMap<String, Integer[]> fetchAllTeamTurnovers();

    public int[] fetchPlayerRushingStats(String player);

    public int[] fetchPlayerPassingStats(String player);

    public int[] fetchPlayerReceivingStats(String player);

    public int[] fetchPlayerRushingTDs(String player);

    public int[] fetchPlayerReceivingTDs (String player);

    public int[] fetchPlayerPassingTDs(String player);

    public int[] fetchPlayerReceptions(String player);

    public int[] fetchPlayerTurnovers(String player);
}