package com.enlaces;

import util.Consola;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        Map map = new Map();
        int lineCounter = 1;

        FileReader fileReader = new FileReader("src/com/data/input");
        BufferedReader in = new BufferedReader(fileReader);

        Pattern pattern = Pattern.compile("((^[A-z][A-z0-9]{0,14})[\\s]*(<-|->|<=|=>|-)[\\s]*([A-z][A-z0-9]{0,14})[\\s]*([.?]))(.*)");

        String data;
        while ((data = in.readLine()) != null) {
            Matcher m = pattern.matcher(data);
            if (m.find()) {
                String city1 = m.group(2);
                String operator = m.group(3);
                String city2 = m.group(4);
                String dotMark = m.group(5);

                boolean doktMarkCheck = ((operator.equals("<=") || operator.equals("=>")) && dotMark.equals(".")) || ((operator.equals("<-") || operator.equals("->")) && dotMark.equals("?"));
                if(doktMarkCheck) continue;

                map.createCityIfDoesNotExists(city1);
                map.createCityIfDoesNotExists(city2);

                switch (operator) {
                    case "->":
                        map.createRelation(city1, city2);
                        break;
                    case "<-":
                        map.createRelation(city2, city1);
                        break;
                    case "=>":
                        System.out.println(map.searchWayToCityBFS(city1, city2) + " " + city1 + " => " + city2 + Consola.Color.RESET);
                        break;
                    case "<=":
                        System.out.println(map.searchWayToCityBFS(city2, city1) + " " + city2 + " => " + city1 + Consola.Color.RESET);
                        break;
                    case "-":
                        map.closeConnectionBetween(city1, city2);
                        break;
                }
            }
            lineCounter++;
        }
    }
}
