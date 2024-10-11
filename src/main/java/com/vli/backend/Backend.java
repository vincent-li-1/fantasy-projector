package com.vli.backend;

public class Backend implements BackendInterface {
    private static Backend instance;

    private Backend() {}

    public static Backend getInstance() {
        if (instance == null) {
            synchronized (Backend.class) {
                if (instance == null) {
                    instance = new Backend();
                }
            }
        }
        return instance;
    }
    
    public float getProjectionAsString(String player) {
        return 0.0f;
    }

    public void setWeek(int week) {
        return;
    }
}