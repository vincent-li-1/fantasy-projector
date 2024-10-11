package com.vli.backend;

import java.util.HashMap;

public interface TeamDatabaseInterface {

    public HashMap<String, Team> getDatabase();

    public Team getTeam(String teamCode);

    public void upsertTeam(String teamCode, Team team);

}