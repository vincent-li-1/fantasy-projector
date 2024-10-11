package com.vli.app;

import java.util.Scanner;
import com.vli.backend.*;
import com.vli.frontend.*;

public class App {
    public static void main(String[] args) {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        frontend.start();
    }
}