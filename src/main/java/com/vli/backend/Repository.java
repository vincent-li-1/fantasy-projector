package com.vli.backend;

import java.util.HashMap;

public class Repository implements RepositoryInterface {

    private String url = "https://api.sportsdata.io/v3/nfl/stats/json";
    private int week;

    public Repository(int week) {
        this.week = week;
    }

    public HashMap<String, String[]> fetchTeamOpponents(String team) {
        HashMap<String, String[]> map = new HashMap<>();
        return map;
    }

    public HashMap<String, Integer[]> fetchAllTeamPassingStats() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    }

    public HashMap<String, Integer[]> fetchAllTeamRushingStats() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    } 

    public HashMap<String, Integer[]> fetchAllTeamPassingTDs() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    }

    public HashMap<String, Integer[]> fetchAllTeamRushingTDs() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    }

    public HashMap<String, Integer[]> fetchAllTeamReceptions() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    }

    public HashMap<String, Integer[]> fetchAllTeamTurnovers() {
        HashMap<String, Integer[]> map = new HashMap<>();
        return map;
    }

    public int[] fetchPlayerRushingStats(String player) {
        return new int[3];
    };

    public int[] fetchPlayerPassingStats(String player) {
        return new int[3];
    };

    public int[] fetchPlayerReceivingStats(String player) {
        return new int[3];
    };

    public int[] fetchPlayerRushingTDs(String player) {
        return new int[3];
    };

    public int[] fetchPlayerReceivingTDs (String player) {
        return new int[3];
    };

    public int[] fetchPlayerPassingTDs(String player) {
        return new int[3];
    };

    public int[] fetchPlayerReceptions(String player){
        return new int[3];
    };

    public int[] fetchPlayerTurnovers(String player){
        return new int[3];
    };
}