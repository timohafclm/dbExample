package com.company.database;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        while (true){
            try {
                return reader.readLine();
            } catch (IOException e) {
                System.out.println("Error. Repeat input.");
            }
        }
    }
}
