package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import newfacts.Fact;


public class FactReader {

    public static List<Fact> readFactsFromFile(String fileName) {
        List<Fact> factList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String author = parts[0].trim();
                    String type = parts[1].trim();
                    String text = parts[2].trim();
                    Fact fact = new Fact(author, type, text);
                    factList.add(fact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return factList;
    }

    public static void main(String[] args) {
        List<Fact> factList = readFactsFromFile("data.txt");
        for (Fact fact : factList) {
            System.out.println(fact);
        }
    }

}
