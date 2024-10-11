package com.vli.backend;

public interface BackendInterface {
    public float getProjectionAsString(String player);

    public void setWeek(int week);

    public void loadTeamData(int week);
}