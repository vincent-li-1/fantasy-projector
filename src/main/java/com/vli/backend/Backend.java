package com.vli.backend;

public class Backend implements BackendInterface {
    private static Backend instance;
    public float scoring = 0.5f;

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
    
    public float getProjectionAsString(String player) {
        return 0.0f;
    }

    public void loadTeamData(int week, int season) {
        Repository repo = new Repository();
        try {
            repo.loadTeamDataIntoDatabase(week, season);
        } catch (Exception e) {
            System.out.println(e);
        }
        TeamDatabase database = TeamDatabase.getInstance();
        return;
    }
}