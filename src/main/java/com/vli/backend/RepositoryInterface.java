package com.vli.backend;

import java.util.HashMap;
import java.net.MalformedURLException;
import java.io.IOException;

public interface RepositoryInterface {

    public void loadTeamDataIntoDatabase(int week, int season) throws MalformedURLException, IOException;
}