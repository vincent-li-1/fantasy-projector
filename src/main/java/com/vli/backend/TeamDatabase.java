package com.vli.backend;

import java.util.HashMap;

public class TeamDatabase implements TeamDatabaseInterface {
    private static TeamDatabase instance;
    
    private HashMap<String, Team> database;

    private TeamDatabase() {
        this.database = new HashMap<>();
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

    public void upsertTeam(String teamCode, Team team) {
        this.database.put(teamCode, team);
    }

    public void clear() {
        this.database.clear();
    }

    public void deleteTeam(String teamCode) {
        this.database.remove(teamCode);
    }
}