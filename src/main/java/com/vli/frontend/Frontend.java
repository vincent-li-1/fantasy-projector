package com.vli.frontend;

import java.util.Scanner;
import com.vli.backend.*;

public class Frontend implements FrontendInterface{
    // TODO: input week and maybe season, input scoring format

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
        Backend backend = Backend.getInstance();
        backend.loadTeamData(2);
        // promptWeek();
        mainMenu();
    }

    // public void promptWeek() {
    //     System.out.println("\nPlease enter the NFL week you would like a projection for, or type \"exit\" to exit:\n");
    //     String input;
    //     boolean exit = false;
    //     while (scanner.hasNextLine()) {
    //         input = scanner.nextLine().trim().toLowerCase();
    //         if ()
    //         setWeek(input);
    //     }
        
    // }

    public void mainMenu() {
        System.out.println("\nPlease enter the name of a player or type \"exit\" to exit the application:\n");
        String userInput;
        boolean exit = false;
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equals("exit")) {
                exit = true;
                break;
            }
            inputPlayer(userInput);
            System.out.println("\nPlease enter the name of another player or type \"exit\" to exit the application:\n");
        }
        exitApp();
    }

    public void exitApp() {
        System.out.println("\nShutting down...");
        scanner.close();
    }

    public void inputPlayer(String player) {
        float projectedPoints = Backend.getInstance().getProjectionAsString(player);
        System.out.println("\nProjected points: " + String.valueOf(projectedPoints));
    }
}