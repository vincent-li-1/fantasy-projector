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

    @BeforeAll
    public static void init() {
        int[] oRS = new int[]{100, 200, 100};
        int[] oPS = new int[]{350, 300, 400};
        int[] oRTD = new int[]{1, 0, 2};
        int[] oPTD = new int[]{3, 5, 2};
        int[] oTO = new int[]{0, 1, 0};
        int[] oREC = new int[]{40, 34, 28};
        int[] dRS = new int[]{70, 60, 89};
        int[] dPS = new int[]{200, 220, 265};
        int[] dRTD = new int[]{3, 3, 2};
        int[] dPTD = new int[]{1, 0, 4};
        int[] dTO = new int[]{1, 0, 0};
        int[] dREC = new int[]{30, 33, 38};
        String[] schedule1 = new String[]{"DEN", "TB", "SF"};
        String code1 = "NO";
        String location1 = "New Orleans";
        String teamName1 = "Saints";
        String[] schedule2 = new String[]{"NO", "LV", "BAL"};
        String code2 = "DEN";
        String location2 = "Denver";
        String teamName2 = "Broncos";
        String[] schedule3 = new String[]{"ATL", "NO", "CIN"};
        String code3 = "TB";
        String location3 = "Tampa Bay";
        String teamName3 = "Buccaneers";
        String[] schedule4 = new String[]{"LAR", "SEA", "NO"};
        String code4 = "SF";
        String location4 = "San Francisco";
        String teamName4 = "49ers";
        saints = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS, dPS, dRTD, dPTD, dTO, dREC, schedule1, code1, location1, teamName1
        );
        broncos = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS, dPS, dRTD, dPTD, dTO, dREC, schedule2, code2, location2, teamName2
        );
        bucs = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS, dPS, dRTD, dPTD, dTO, dREC, schedule3, code3, location3, teamName3
        );
        niners = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS, dPS, dRTD, dPTD, dTO, dREC, schedule4, code4, location4, teamName4
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
        float[] expectedDefStats = new float[]{73f, 228.33f, 2.67f, 1.67f, 0.33f, 33.67f};
        float[] averageDefStats = saints.averageOpponentDefensiveStats(saints.getOpponentDefensiveStats("DEN"));
        for (int i = 0; i < expectedDefStats.length; i++) {
            assertEquals(expectedDefStats[i], averageDefStats[i], 0.01f);
        }
    }
}
