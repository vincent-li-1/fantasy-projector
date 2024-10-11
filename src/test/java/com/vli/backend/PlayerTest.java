package com.vli.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {

    private static Team mockTeam;
    private static float[] expectedStats = new float[]{86f, 235.7f, 0.9f, 3.22f, 1.1f, 33.8f};
    private static TeamDatabase database = TeamDatabase.getInstance();
    private static Player testPlayer;

    @BeforeAll
    public static void init() {
        Team mockTeam = mock(Team.class);
        when(mockTeam.expectedStatsAgainstNextOpp()).thenReturn(expectedStats);
        int[] oRY = new int[]{100, 200, 100};
        int[] oPY = new int[]{350, 300, 400};
        int[] oRTD = new int[]{1, 0, 2};
        int[] oPTD = new int[]{3, 5, 2};
        int[] oTO = new int[]{0, 1, 0};
        int[] oREC = new int[]{40, 34, 28};
        int[][] teamStats = new int[][]{oRY, oPY, oRTD, oPTD, oTO, oREC};
        mockTeam.offensiveStats = teamStats;
        mockTeam.code = "NO";
        database.clear();
        database.upsertTeam("NO", mockTeam);

        int[] RY = new int[]{80, 143, 77};
        int[] PY = new int[]{0, 13, 0};
        int[] REY = new int[]{22, 7, 31};
        int[] RTD = new int[]{0, 0, 2};
        int[] PTD = new int[]{0, 1, 0};
        int[] RETD = new int[]{1, 0, 0};
        int[] TO = new int[]{0, 0, 0};
        int[] REC = new int[]{2, 1, 3};
        int[][] stats = new int[][]{RY, PY, REY, RTD, PTD, RETD, TO, REC};
        testPlayer = new Player(stats, database.getTeam("NO"), "Alvin Kamara", "RB");
    }

    @Test
    public void testGetTeamStats() {
        int [][] expectedTeamStats = new int[][]{{100, 200, 100}, {350, 300, 400}, {350, 300, 400}, {1, 0, 2}, {3, 5, 2}, {3, 5, 2}, {0, 1, 0}, {40, 34, 28}};
        int[][] teamStats = testPlayer.getTeamStatsWithReceiving();
        assertArrayEquals(teamStats, expectedTeamStats);
    }

    @Test
    public void testCalculateWeightedTeamStatCoefficients() {
        float[] expectedCoefficients = new float[]{0.76f, 0.02f, 0.06f, 0.43f, 0.07f, 0.07f, 0f, 0.07f};
        float[] actualCoefficients = testPlayer.calculateWeightedTeamStatCoefficients();
        assertArrayEquals(expectedCoefficients, actualCoefficients, 0.01f);
    }

    @Test
    public void testProjectStatsAgainstNextOpp() {
        float[] coefficients = testPlayer.calculateWeightedTeamStatCoefficients();
        float[] expectedProjection = new float[]{coefficients[0] * 86f, coefficients[1] * 235.7f, coefficients[2] * 235.7f, coefficients[3] * 0.9f, coefficients[4] * 3.22f, coefficients[5] * 3.22f, coefficients[6] * 1.1f, coefficients[7] * 33.8f};
        float[] actualProjection = testPlayer.projectStatsAgainstNextOpp();
        assertArrayEquals(expectedProjection, actualProjection, 0.1f);
    }

    @Test
    public void testProjectPointsAgainstNextOpp() {
        float[] statsProj = testPlayer.projectStatsAgainstNextOpp();
        float[] stdPts = new float[]{0.1f, 0.04f, 0.1f, 6f, 4f, 6f, -2f, 0f};
        float[] halfPts = new float[]{0.1f, 0.04f, 0.1f, 6f, 4f, 6f, -2f, 0.5f};
        float[] fullPts = new float[]{0.1f, 0.04f, 0.1f, 6f, 4f, 6f, -2f, 1f};
        float expectedStdPts = 0;
        float expectedHalfPts = 0;
        float expectedFullPts = 0;
        for (int i = 0; i < statsProj.length; i++) {
            expectedStdPts += stdPts[i] * statsProj[i];
            expectedHalfPts += halfPts[i] * statsProj[i];
            expectedFullPts += fullPts[i] * statsProj[i];
        }
        float actualStdPts = testPlayer.projectPointsAgainstNextOpp(0f);
        float actualHalfPts = testPlayer.projectPointsAgainstNextOpp(0.5f);
        float actualFullPts = testPlayer.projectPointsAgainstNextOpp(1f);

        assertEquals(expectedStdPts, actualStdPts);
        assertEquals(expectedHalfPts, actualHalfPts);
        assertEquals(expectedFullPts, actualFullPts);
    }
}