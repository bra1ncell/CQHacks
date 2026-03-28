package Project;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Main {

    static void main() {
        LapTimesReader LTReader = new LapTimesReader("src/main/java/resources/LapTimes.csv");
        ArrayList<Race> races = LTReader.readCSV();
        System.out.println(races.get(0).toString());
        InsightGenerator ingen = new InsightGenerator(races.get(0));
        ingen.generateAll();
        for (String a : ingen.insights) {
            System.out.println(a);
        }
        /*for (Race race : races) {
            System.out.println(race.toString());
        }*/
    }


}
