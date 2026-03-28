package Project;

import java.io.BufferedReader;

public class Main {

    static void main() {
        ArrayList<Race> races = LapTimesReader("LapTimes.csv");
        for (Race race : races) {
            System.out.println(race.toString());
        }
    }


}
