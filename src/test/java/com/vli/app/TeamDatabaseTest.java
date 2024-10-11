package com.vli.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TeamDatabaseTest {

    private static TeamDatabase database;
    private static Team mockTeam;

    @BeforeAll
    public static void init() {
        mockTeam = mock(Team.class);
        database = TeamDatabase.getInstance();
    }

    @AfterEach
    public void clearDatabase() {
        database.clear();
    }

    @Test
    public void testDatabaseIsSingleton() {
        TeamDatabase database2 = TeamDatabase.getInstance();
        TeamDatabase database3 = TeamDatabase.getInstance();
        assertSame(database, database2);
        assertSame(database, database3);
    }

    @Test
    public void testGetDatabase() {
        HashMap<String, Team> testMap= new HashMap<>();
        assertEquals(testMap, database.getDatabase());
    }
    
    @Test
    public void testGetTeam() {
        database.upsertTeam("test", mockTeam);
        Team fetchedTeam = database.getTeam("test");
        assertEquals(mockTeam, fetchedTeam);
    }

    @Test
    public void testAddTeam() {
        database.upsertTeam("test", mockTeam);
        HashMap<String, Team> testMap = new HashMap<>();
        testMap.put("test", mockTeam);
        assertEquals(testMap, database.getDatabase());
    }

    @Test
    public void testUpdateTeam() {
        database.upsertTeam("test", mockTeam);
        Team mockTeam2 = mock(Team.class);
        database.upsertTeam("test", mockTeam2);
        assertSame(mockTeam2, database.getTeam("test"));
    }

    @Test
    public void testGetNonexistentTeam() {
        database.upsertTeam("test", mockTeam);
        assertNull(database.getTeam("notTeam"));
    }

    @Test
    public void testClear() {
        database.upsertTeam("test", mockTeam);
        database.clear();
        assertNull(database.getTeam("test"));
        assertEquals(database.getDatabase().size(), 0);
    }

    @Test
    public void testDeleteTeam() {
        database.upsertTeam("test", mockTeam);
        database.upsertTeam("test2", mockTeam);
        database.deleteTeam("test2");
        assertSame(database.getTeam("test"), mockTeam);
        assertNull(database.getTeam("test2"));
        assertEquals(database.getDatabase().size(), 1);
    }
}
