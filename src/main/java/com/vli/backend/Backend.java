package com.vli.backend;

public class Backend implements BackendInterface {
    private static Backend instance;
    public float scoring = 0.5f;
    public int week;
    public int season;

    private Backend() {}

    public static Backend getInstance() {
        if (instance == null) {
            synchronized (Backend.class) {
                if (instance == null) {
                    instance = new Backend();
                }
            }
        }
        return instance;
    }

    public Player getPlayer(String playerName) {
        Repository repo = new Repository();
        Player player;
        try {
            player = repo.getPlayerFromName(playerName, week, season);
            player.projection = player.projectPointsAgainstNextOpp(scoring);
            return player;
        } catch (Exception e) {
        }
        return null;
    }
    
    public float getProjectionAsString(String playerName) {
        Repository repo = new Repository();
        Player player;
        try {
            player = repo.getPlayerFromName(playerName, week, season);
            return player.projectPointsAgainstNextOpp(scoring);
        } catch (Exception e) {
        }
        return 0.0f;
    }

    public void loadTeamData() {
        Repository repo = new Repository();
        try {
            repo.loadTeamDataIntoDatabase(week, season);
        } catch (Exception e) {
        }
        TeamDatabase database = TeamDatabase.getInstance();
        return;
    }
}