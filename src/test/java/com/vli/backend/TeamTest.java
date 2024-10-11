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
    private static TeamDatabase database;

    // TODO: Make the data less lazy
    @BeforeAll
    public static void init() {
        int[] oRS = new int[]{100, 200, 100};
        int[] oPS = new int[]{350, 300, 400};
        int[] oRTD = new int[]{1, 0, 2};
        int[] oPTD = new int[]{3, 5, 2};
        int[] oTO = new int[]{0, 1, 0};
        int[] oREC = new int[]{40, 34, 28};
        int[] dRS1 = new int[]{70, 60, 89};
        int[] dPS1 = new int[]{200, 220, 265};
        int[] dRTD1 = new int[]{3, 3, 2};
        int[] dPTD1 = new int[]{1, 0, 4};
        int[] dTO1 = new int[]{1, 0, 0};
        int[] dREC1 = new int[]{30, 33, 38};
        String[] schedule1 = new String[]{"DEN", "TB", "SF"};
        String code1 = "NO";
        String location1 = "New Orleans";
        String teamName1 = "Saints";
        saints = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS1, dPS1, dRTD1, dPTD1, dTO1, dREC1, schedule1, code1, location1, teamName1
        );

        int[] dRS2 = new int[]{100, 90, 100};
        int[] dPS2 = new int[]{350, 200, 186};
        int[] dRTD2 = new int[]{1, 2, 1};
        int[] dPTD2 = new int[]{3, 0, 4};
        int[] dTO2 = new int[]{0, 2, 6};
        int[] dREC2 = new int[]{40, 25, 12};
        String[] schedule2 = new String[]{"NO", "BAL", "LV"};
        String code2 = "DEN";
        String location2 = "Denver";
        String teamName2 = "Broncos";
        broncos = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS2, dPS2, dRTD2, dPTD2, dTO2, dREC2, schedule2, code2, location2, teamName2
        );

        int[] dRS3 = new int[]{50, 200, 140};
        int[] dPS3 = new int[]{290, 300, 100};
        int[] dRTD3 = new int[]{1, 0, 3};
        int[] dPTD3 = new int[]{3, 5, 1};
        int[] dTO3 = new int[]{0, 1, 1};
        int[] dREC3 = new int[]{40, 33, 28};
        String[] schedule3 = new String[]{"ATL", "NO", "CIN"};
        String code3 = "TB";
        String location3 = "Tampa Bay";
        String teamName3 = "Buccaneers";
        bucs = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS3, dPS3, dRTD3, dPTD3, dTO3, dREC3, schedule3, code3, location3, teamName3
        );
        
        int[] dRS4 = new int[]{122, 100};
        int[] dPS4 = new int[]{450, 400};
        int[] dRTD4 = new int[]{1, 2};
        int[] dPTD4 = new int[]{1, 2};
        int[] dTO4 = new int[]{2, 0};
        int[] dREC4 = new int[]{15, 28};
        String[] schedule4 = new String[]{"LAR", "NO"};
        String code4 = "SF";
        String location4 = "San Francisco";
        String teamName4 = "49ers";
        niners = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS4, dPS4, dRTD4, dPTD4, dTO4, dREC4, schedule4, code4, location4, teamName4
        );
        

        database = TeamDatabase.getInstance();
        database.upsertTeam("NO", saints);
        database.upsertTeam("DEN", broncos);
        database.upsertTeam("TB", bucs);
        database.upsertTeam("SF", niners);
    }

    @Test
    public void testGetDefensiveStat() {
        assertSame(saints.getOpponentDefensiveStats("DEN")[0], broncos.dRS);
        assertSame(saints.getOpponentDefensiveStats("DEN")[1], broncos.dPS);
        assertSame(saints.getOpponentDefensiveStats("DEN")[2], broncos.dRTD);
        assertSame(saints.getOpponentDefensiveStats("DEN")[3], broncos.dPTD);
        assertSame(saints.getOpponentDefensiveStats("DEN")[4], broncos.dTO);
        assertSame(saints.getOpponentDefensiveStats("DEN")[5], broncos.dREC);
        assertSame(saints.getOpponentDefensiveStats("TB")[0], bucs.dRS);
        assertSame(saints.getOpponentDefensiveStats("SF")[0], niners.dRS);
    }
    
    @Test
    public void testAverageOpponentDefensiveStats() {
        float[] expectedDefStats = new float[]{96.67f, 245.33f, 1.33f, 2.33f, 2.67f, 25.67f};
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
}
