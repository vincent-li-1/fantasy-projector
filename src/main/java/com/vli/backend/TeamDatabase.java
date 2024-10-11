package com.vli.backend;

import java.util.HashMap;

public class TeamDatabase implements TeamDatabaseInterface {
    private static TeamDatabase instance;
    
    private HashMap<String, Team> database;

    private TeamDatabase() {
    }

    public static TeamDatabase getInstance() {
        if (instance == null) {
            synchronized (TeamDatabase.class) {
                if (instance == null) {
                    instance = new TeamDatabase();
                }
            }
        }
        return instance;
    }

    public HashMap<String, Team> getDatabase() {
        return this.database;
    }

    public Team getTeam(String teamCode) {
        return this.database.get(teamCode);
    }
}