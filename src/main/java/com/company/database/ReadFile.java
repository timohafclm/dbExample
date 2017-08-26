package com.company.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {
    private static Map<String, Integer> map = new HashMap<>();

    /**
     * Исключения здесь никак не обрабытываю, т.к. по факту файлы захардкожены
     */
    public static Map<String, Integer> getData(String fileName) {
        try {
            readFile(fileName);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void readFile(String fileName) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            map.clear();
            String[] buffer;
            while (reader.ready()) {
                buffer = reader.readLine().split("=");
                map.put(buffer[0], Integer.parseInt(buffer[1]));
            }
        }
    }
}
