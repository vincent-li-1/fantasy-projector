package com.vli.frontend;

import java.util.Scanner;
import com.vli.backend.*;
import java.time.Year;
import java.text.DecimalFormat;

public class Frontend implements FrontendInterface{
    private static Frontend instance;

    private Scanner scanner;

    private Frontend() {
        this.scanner = new Scanner(System.in);
    }

    public static Frontend getInstance() {
        if (instance == null) {
            synchronized (Frontend.class) {
                if (instance == null) {
                    instance = new Frontend();
                }
            }
        }
        return instance;
    }

    public void start() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        System.out.println("\nWelcome to the Fantasy Football Projector");
        loadData();
        setScoring();
        mainMenu();
    }

    public void loadData() {
        System.out.println("\nPlease enter the NFL season and week to project, formatted as \"2024-2\":\n");
        String input;
        int[] inputInts = new int[2];
        while (scanner.hasNextLine()) {
            input = scanner.nextLine().trim().toLowerCase();
            String[] inputs = input.split("-");
            if (inputs.length != 2) {
                System.out.println("\nInvalid input, please try again:\n");
            }
            try {
                inputInts[0] = Integer.parseInt(inputs[0]);
                inputInts[1] = Integer.parseInt(inputs[1]);
                // TODO: Use sports data API to get the exact year/week entries
                if (inputInts[0] < 2000 || inputInts[0] > Year.now().getValue()) {
                    System.out.println("\nCan only use seasons between 2000 and " + Year.now().getValue() + ", please try again:\n");
                }
                else if (inputInts[1] < 2 || inputInts[1] > 17) {
                    System.out.println("\nCan only use weeks between 2 and 17, please try again:\n");
                }
                else {
                    System.out.println("\nLoading data...");
                    Backend backend = Backend.getInstance();
                    backend.week = inputInts[1];
                    backend.season = inputInts[0];
                    backend.loadTeamData();
                    break;
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input, please try again:\n");
            }
        }
    }

    public void mainMenu() {
        String currentScoring;
        if (Backend.getInstance().scoring == 0f) {
            currentScoring = "Standard";
        }
        else if (Backend.getInstance().scoring == 0.5f) {
            currentScoring = "Half-PPR";
        }
        else if (Backend.getInstance().scoring == 1f) {
            currentScoring = "PPR";
        }
        else {
            currentScoring = "None";
        }
        String userInput;
        System.out.println("\nScoring is " + currentScoring + ". Season and week is " + Integer.toString(Backend.getInstance().season) + "-" + Integer.toString(Backend.getInstance().week) + ". Please enter the name of a player or type \"change\" to change the season or week, \"scoring\" to change the scoring, or \"exit\" to exit:\n");
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equals("exit")) {
                break;
            }
            else if (userInput.equals("change")) {
                loadData();
                System.out.println("\nScoring is " + currentScoring + ". Please enter the name of another player or type \"change\" to change the season or week, \"scoring\" to change the scoring, or \"exit\" to exit:\n");
            }
            else if (userInput.equals("scoring")) {
                setScoring();
                System.out.println("\nScoring is " + currentScoring + ". Please enter the name of another player or type \"change\" to change the season or week, \"scoring\" to change the scoring, or \"exit\" to exit:\n");
            }
            else {
                System.out.println("\nCalculating...");
                inputPlayer(userInput);
                System.out.println("\nScoring is " + currentScoring + ". Please enter the name of another player or type \"change\" to change the season or week, \"scoring\" to change the scoring, or \"exit\" to exit:\n");
            } 
        }
        exitApp();
    }

    public void exitApp() {
        System.out.println("\nShutting down...");
        scanner.close();
    }

    public void inputPlayer(String playerName) {
        Player player = Backend.getInstance().getPlayer(playerName);
        DecimalFormat df = new DecimalFormat("0.00");
        if (player == null) {
            System.out.println("\nPlayer not found");
        }
        else {
            System.out.println("\nPlayer: " + player.name + "\nPosition: " + player.position + "\nTeam: " + player.team.code + "\nOpponent: " + player.team.nextOpp + "\nProjected points: " + df.format(player.projection));
        }
    }

    public void setScoring() {
        String currentScoring;
        if (Backend.getInstance().scoring == 0f) {
            currentScoring = "Standard";
        }
        else if (Backend.getInstance().scoring == 0.5f) {
            currentScoring = "Half-PPR";
        }
        else if (Backend.getInstance().scoring == 1f) {
            currentScoring = "PPR";
        }
        else {
            currentScoring = "None";
        }
        System.out.println("\nPlease enter the number corresponding to your desired scoring method. Current method is: " + currentScoring + "\n1: Standard\n2: Half-PPR\n3: PPR\n");
        String input;
        boolean entered = false;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine().trim().toLowerCase();
            switch(input) {
                case "1":
                    Backend.getInstance().scoring = 0f;
                    entered = true;
                    break;
                case "2":
                    Backend.getInstance().scoring = 0.5f;
                    entered = true;
                    break;
                case"3":
                    Backend.getInstance().scoring = 1f;
                    entered = true;
                    break;
                default:
                    System.out.println("Error: Invalid input, please try again:");
            }
            if (entered) {
                break;
            }
        }
    }
}