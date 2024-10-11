package com.vli.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

public class TeamTest {

    private static Team team;
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
        String[] schedule = new String[]{"DEN", "TB", "NO"};
        String code = "SF";
        String location = "San Francisco";
        String teamName = "49ers";
        team = new Team(
            oRS, oPS, oRTD, oPTD, oTO, oREC, dRS, dPS, dRTD, dPTD, dTO, dREC, schedule, code, location, teamName
        );

        database = TeamDatabase.getInstance();
    }

    

}
