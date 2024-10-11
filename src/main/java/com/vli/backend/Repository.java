package com.vli.backend;

import java.util.HashMap;
import org.json.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.Scanner;

public class Repository implements RepositoryInterface {
    public void loadTeamDataIntoDatabase(int week) 
        throws MalformedURLException, IOException {
        for (int i = 0; i < week; i++) {
            URL url = new URL("https://api.sportsdata.io/v3/nfl/stats/json/TeamGameStatsFinal/2024/" + Integer.toString(i) + "?key=daa9686491a84209b4f7850b9cd67b6e");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HTTPResponseCode: " + responseCode);
            }

            String jsonString = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                jsonString += scanner.nextLine();
            }

            scanner.close();

            JSONArray jsonArr = new JSONArray(jsonString);

            for (int j = 0; j < jsonArr.length(); j++) {
                processGame(jsonArr.getJSONObject(j));
            }
        }
        URL url = new URL("https://api.sportsdata.io/v3/nfl/scores/json/SchedulesBasic/2024?key=daa9686491a84209b4f7850b9cd67b6e");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HTTPResponseCode: " + responseCode);
        }

        String jsonString = "";
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            jsonString += scanner.nextLine();
        }

        scanner.close();

        JSONArray jsonArr = new JSONArray(jsonString);

        for (int i = 0; i < jsonArr.length(); i++) {
            if (jsonArr.getJSONObject(i).getInt("Week") > week) {
                break;
            }
            if (jsonArr.getJSONObject(i).getInt("Week") == week) {
                processSchedule(jsonArr.getJSONObject(i));
            }
        }
    }

    private void processSchedule(JSONObject game) {
        String homeTeam = game.getString("HomeTeam");
        String awayTeam = game.getString("AwayTeam");
        addNextOpponent(homeTeam, awayTeam);
        addNextOpponent(awayTeam, homeTeam);
    }

    private void addNextOpponent(String teamCode, String opp) {
        TeamDatabase database = TeamDatabase.getInstance();
        Team team = database.getTeam(teamCode);
        team.nextOpp = opp;
        database.upsertTeam(teamCode, team);
    };

    private void processGame(JSONObject game) {
        String team = game.getString("Team");
        String opp = game.getString("Opponent");
        String teamName = game.getString("TeamName");
        int[] teamOffStats = new int[]{game.getInt("RushingYards"), game.getInt("PassingYards"), game.getInt("RushingTouchdowns"), game.getInt("PassingTouchdowns"), game.getInt("Giveaways"), game.getInt("PassingCompletions")};
        int[] oppOffStats = new int[]{game.getInt("OpponentRushingYards"), game.getInt("OpponentPassingYards"), game.getInt("OpponentRushingTouchdowns"), game.getInt("OpponentPassingTouchdowns"), game.getInt("OpponentGiveaways"), game.getInt("OpponentPassingCompletions")};
        createOrAddToTeam(team, opp, teamOffStats, oppOffStats, teamName);
    }

    private void createOrAddToTeam(String teamCode, String oppCode, int[] teamOffStats, int[] teamDefStats, String teamName) {
        TeamDatabase database = TeamDatabase.getInstance();
        if (database.getTeam(teamCode) == null) {
            createTeam(teamCode, teamName);
        }
        Team team = database.getTeam(teamCode);
        team.addWeek(teamOffStats, teamDefStats, oppCode);
    }

    private void createTeam(String teamCode, String teamName) {
        TeamDatabase database = TeamDatabase.getInstance();
        Team team = new Team(new int[6][0], new int[6][0], new String[0], teamCode, teamName, "");
        database.upsertTeam(teamCode, team);
    }
}