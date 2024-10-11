package com.vli.backend;

import java.util.HashMap;

public class Calculator implements CalculatorInterface {
    private static Calculator instance;

    private Calculator() {
    }

    public static Calculator getInstance() {
        if (instance == null) {
            synchronized (Calculator.class) {
                if (instance == null) {
                    instance = new Calculator();
                }
            }
        }
        return instance;
    }

    public float calculateTeamCoefficient(String team, String stat) {
        return 0.0f;
    };

    public float calculatePlayerCoefficient(String player, String stat) {
        return 0.0f;
    };

    public float calculateProjection(String player) {
        return 0.0f;
    };
}