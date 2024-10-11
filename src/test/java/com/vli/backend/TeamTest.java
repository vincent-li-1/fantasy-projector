package com.vli.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

public class TeamTest {

    private static Team saints;
    private static Team broncos;
    private static Team bucs;
    private static Team niners;
    private static Team vikings;
    private static TeamDatabase database;

    @BeforeAll
    public static void init() {
        int[] oRY = new int[]{100, 200, 100};
        int[] oPY = new int[]{350, 300, 400};
        int[] oRTD = new int[]{1, 0, 2};
        int[] oPTD = new int[]{3, 5, 2};
        int[] oTO = new int[]{0, 1, 0};
        int[] oREC = new int[]{40, 34, 28};
        int[] dRYds1 = new int[]{70, 60, 89};
        int[] dPYds1 = new int[]{200, 220, 265};
        int[] dRTD1 = new int[]{3, 3, 2};
        int[] dPTD1 = new int[]{1, 0, 4};
        int[] dTO1 = new int[]{1, 0, 0};
        int[] dREC1 = new int[]{30, 33, 38};

        int[][] offensiveStats = new int[][]{oRY, oPY, oRTD, oPTD, oTO, oREC};
        int[][] defensiveStats1 = new int[][]{dRYds1, dPYds1, dRTD1, dPTD1, dTO1, dREC1};
        String[] schedule1 = new String[]{"DEN", "TB", "SF"};
        String code1 = "NO";
        String location1 = "New Orleans";
        String teamName1 = "Saints";
        String nextOpp = "MIN";
        saints = new Team(
            offensiveStats, defensiveStats1, schedule1, code1, location1, teamName1, nextOpp
        );

        int[] dRYds2 = new int[]{100, 90, 100};
        int[] dPYds2 = new int[]{350, 200, 186};
        int[] dRTD2 = new int[]{1, 2, 1};
        int[] dPTD2 = new int[]{3, 0, 4};
        int[] dTO2 = new int[]{0, 0, 0};
        int[] dREC2 = new int[]{40, 25, 12};

        int[][] defensiveStats2 = new int[][]{dRYds2, dPYds2, dRTD2, dPTD2, dTO2, dREC2};
        String[] schedule2 = new String[]{"NO", "BAL", "LV"};
        String code2 = "DEN";
        String location2 = "Denver";
        String teamName2 = "Broncos";
        String nextOpp2 = "DAL";
        broncos = new Team(
            offensiveStats, defensiveStats2, schedule2, code2, location2, teamName2, nextOpp2
        );

        int[] dRYds3 = new int[]{50, 200, 140};
        int[] dPYds3 = new int[]{290, 300, 100};
        int[] dRTD3 = new int[]{1, 0, 3};
        int[] dPTD3 = new int[]{3, 5, 1};
        int[] dTO3 = new int[]{0, 1, 1};
        int[] dREC3 = new int[]{40, 33, 28};

        int[][] defensiveStats3 = new int[][]{dRYds3, dPYds3, dRTD3, dPTD3, dTO3, dREC3};
        String[] schedule3 = new String[]{"ATL", "NO", "CIN"};
        String code3 = "TB";
        String location3 = "Tampa Bay";
        String teamName3 = "Buccaneers";
        String nextOpp3 = "ARI";
        bucs = new Team(
            offensiveStats, defensiveStats3, schedule3, code3, location3, teamName3, nextOpp3
        );
        
        int[] dRYds4 = new int[]{122, 100};
        int[] dPYds4 = new int[]{450, 400};
        int[] dRTD4 = new int[]{1, 2};
        int[] dPTD4 = new int[]{1, 2};
        int[] dTO4 = new int[]{2, 0};
        int[] dREC4 = new int[]{15, 28};

        int[][] defensiveStats4 = new int[][]{dRYds4, dPYds4, dRTD4, dPTD4, dTO4, dREC4};
        String[] schedule4 = new String[]{"LAR", "NO"};
        String code4 = "SF";
        String location4 = "San Francisco";
        String teamName4 = "49ers";
        String nextOpp4 = "NYG";
        niners = new Team(
            offensiveStats, defensiveStats4, schedule4, code4, location4, teamName4, nextOpp4
        );
        
        int[] dRYds5 = new int[]{101, 102};
        int[] dPYds5 = new int[]{501, 502};
        int[] dRTD5 = new int[]{0, 1};
        int[] dPTD5 = new int[]{3, 4};
        int[] dTO5 = new int[]{1, 1};
        int[] dREC5 = new int[]{30, 33};

        int[][] defensiveStats5 = new int[][]{dRYds5, dPYds5, dRTD5, dPTD5, dTO5, dREC5};
        String[] schedule5 = new String[]{"GB", "CHI"};
        String code5 = "MIN";
        String location5 = "Minnesota";
        String teamName5 = "Vikings";
        String nextOpp5 = "NO";
        vikings = new Team(
            offensiveStats, defensiveStats5, schedule5, code5, location5, teamName5, nextOpp5
        );

        database = TeamDatabase.getInstance();
        database.upsertTeam("NO", saints);
        database.upsertTeam("DEN", broncos);
        database.upsertTeam("TB", bucs);
        database.upsertTeam("SF", niners);
        database.upsertTeam("MIN", vikings);
    }

    @Test
    public void testGetDefensiveStat() {
        assertSame(saints.getOpponentDefensiveStats("DEN"), broncos.defensiveStats);
        assertSame(saints.getOpponentDefensiveStats("TB"), bucs.defensiveStats);
    }
    
    @Test
    public void testAverageOpponentDefensiveStats() {
        float[] expectedDefStats = new float[]{96.67f, 245.33f, 1.33f, 2.33f, 0f, 25.67f};
        float[] denAverageDefStats = saints.averageOpponentDefensiveStats(saints.getOpponentDefensiveStats("DEN"));
        for (int i = 0; i < expectedDefStats.length; i++) {
            assertEquals(expectedDefStats[i], denAverageDefStats[i], 0.01f);
        }

        float[] expectedDefStats2 = new float[]{111f, 425f, 1.5f, 1.5f, 1f, 21.5f};
        float[] sfAverageDefStats = saints.averageOpponentDefensiveStats(saints.getOpponentDefensiveStats("SF"));
        for (int i = 0; i < expectedDefStats.length; i++) {
            assertEquals(expectedDefStats2[i], sfAverageDefStats[i], 0.01f);
        }
    }

    @Test
    public void testCalculateTeamCoefficients() {
        float[] expectedTeamCoefficients = new float[]{1.16f, 1.20f, 0.72f, 1.43f, 0.51f, 1.28f};
        float[] actualTeamCoefficients = saints.calculateTeamCoefficients();
        assertArrayEquals(expectedTeamCoefficients, actualTeamCoefficients, 0.1f);
    }

    @Test
    public void testExpectedStatsAgainstOpp() {
        float[] expectedStats = new float[]{116.9f, 588.28f, 0.37f, 5.04f, 0.52f, 39.57f};
        float[] actualStats = saints.expectedStatsAgainstNextOpp();
        assertArrayEquals(expectedStats, actualStats, 0.1f);
    }
}
