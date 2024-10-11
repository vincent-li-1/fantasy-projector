package com.vli.app;

import java.util.Scanner;
import com.vli.backend.*;
import com.vli.frontend.*;

public class App {
    public static void main(String[] args) {
        Frontend frontend = Frontend.getInstance();
        frontend.start();
    }
}