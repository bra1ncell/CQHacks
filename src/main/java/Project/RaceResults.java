package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RaceResultsReader {
    String fileName = "RaceResults.csv";
    public RaceResultsReader (String fileName) {
        this.fileName = fileName;
    }

    /*
    DriverNumber,BroadcastName,Abbreviation,DriverId,TeamName,TeamColor,TeamId,FirstName,
    LastName,FullName,HeadshotUrl,CountryCode,Position,ClassifiedPosition,
    GridPosition,Time,ElapsedTime,Status,Points,Laps,Round,Country,Location,Event Name,Q1,Q2,Q3
    */

    private final int DRIVER_NUMBER = 0;
    private final int BROADCAST_NAME = 1;
    private final int ABBREVIATION = 2;
    private final int DRIVER_ID = 3;
    private final int TEAM_NAME = 4;
    private final int TEAM_COLOR = 5;
    private final int TEAM_ID = 6;
    private final int FIRST_NAME = 7;
    private final int LAST_NAME = 8;
    private final int FULL_NAME = 9;
    private final int HEADSHOT_URL = 10;
    private final int COUNTRY_CODE = 11;
    private final int POSITION = 12;
    private final int CLASSIFIED_POSITION = 13;
    private final int GRID_POSITION = 14;
    private final int TIME = 15;
    private final int ELAPSED_TIME = 16;
    private final int STATUS = 17;
    private final int POINTS = 18;
    private final int LAPS = 19;
    private final int ROUND = 20;
    private final int COUNTRY = 21;
    private final int LOCATION = 22;
    private final int EVENT_NAME = 23;
    private final int Q1 = 24;
    private final int Q2 = 25;
    private final int Q3 = 26;




    void readCSV () {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                for (String token : tokens) {

                }
            }

        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        }
    }
}


