package com.vli.backend;

import java.util.HashMap;
import org.json.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.Scanner;

public class Repository implements RepositoryInterface {
    public Player getPlayerFromName(String name, int week, int season) throws MalformedURLException, IOException {
        int playerId = getPlayerId(name);
        URL url = new URL("https://api.sportsdata.io/v3/nfl/stats/json/PlayerGameStatsBySeason/" + Integer.toString(season) + "/" + Integer.toString(playerId) + "/" + Integer.toString(week - 1) + "?key=daa9686491a84209b4f7850b9cd67b6e");
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

        int[][] stats = createStatsFromJSONArray(jsonArr);

        Team team = TeamDatabase.getInstance().getTeam(jsonArr.getJSONObject(0).getString("Team"));
        
        String DBName = jsonArr.getJSONObject(0).getString("Name");

        String position = jsonArr.getJSONObject(0).getString("Position");

        return new Player(stats, team, DBName, position);
    }

    private int getPlayerId(String name) throws MalformedURLException, IOException {
        int playerId = 0;
        URL url = new URL("https://api.sportsdata.io/v3/nfl/scores/json/PlayersByAvailable?key=daa9686491a84209b4f7850b9cd67b6e");
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
            if (jsonArr.getJSONObject(i).getString("Name").toLowerCase().equals(name.toLowerCase())) {
                playerId = jsonArr.getJSONObject(i).getInt("PlayerID");
                break;
            }
        }
        return playerId;
    }

    private int[][] createStatsFromJSONArray(JSONArray jsonArr) {
        int[][] stats = new int[8][jsonArr.length()];
        for (int i = 0; i < jsonArr.length(); i++) {
            stats[0][i] = jsonArr.getJSONObject(i).getInt("RushingYards");
            stats[1][i] = jsonArr.getJSONObject(i).getInt("PassingYards");
            stats[2][i] = jsonArr.getJSONObject(i).getInt("ReceivingYards");
            stats[3][i] = jsonArr.getJSONObject(i).getInt("RushingTouchdowns");
            stats[4][i] = jsonArr.getJSONObject(i).getInt("PassingTouchdowns");
            stats[5][i] = jsonArr.getJSONObject(i).getInt("ReceivingTouchdowns");
            stats[6][i] = jsonArr.getJSONObject(i).getInt("FumblesLost") + jsonArr.getJSONObject(i).getInt("Interceptions");
            stats[7][i] = jsonArr.getJSONObject(i).getInt("Receptions");
        }
        return stats;
    }

    public void loadTeamDataIntoDatabase(int week, int season) throws MalformedURLException, IOException {
        TeamDatabase.getInstance().clear();
        for (int i = 0; i < week; i++) {
            URL url = new URL("https://api.sportsdata.io/v3/nfl/stats/json/TeamGameStatsFinal/" + Integer.toString(season) + "/" + Integer.toString(i) + "?key=daa9686491a84209b4f7850b9cd67b6e");
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
        URL url = new URL("https://api.sportsdata.io/v3/nfl/scores/json/SchedulesBasic/" + Integer.toString(season) + "?key=daa9686491a84209b4f7850b9cd67b6e");
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

        int weekIndex = binarySearchWeekInJSONArray(jsonArr, week);

        while (jsonArr.getJSONObject(weekIndex).getInt("Week") == week) {
            processSchedule(jsonArr.getJSONObject(weekIndex));
            weekIndex++;
        }
    }

    private void processSchedule(JSONObject game) {
        String homeTeam = game.getString("HomeTeam");
        String awayTeam = game.getString("AwayTeam");
        if (!homeTeam.equals("BYE")) {
            addNextOpponent(homeTeam, awayTeam);
            if (!awayTeam.equals("BYE")) {
                addNextOpponent(awayTeam, homeTeam);
            }
        }
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

    private int binarySearchWeekInJSONArray(JSONArray arr, int week) {
        int left = 0;
        int right = arr.length() - 1;
        int middle;
        while (right >= left) {
            middle = (right + left) / 2;
            if (arr.getJSONObject(middle).getInt("Week") > week) {
                right = middle - 1;
            }
            else if (arr.getJSONObject(middle).getInt("Week") < week) {
                left = middle + 1;
            }
            else {
                if (arr.getJSONObject(middle - 1).getInt("Week") == week) {
                    right = middle - 1;
                }
                else {
                    return middle;
                }
            }
        }
        return -1;
    }
}