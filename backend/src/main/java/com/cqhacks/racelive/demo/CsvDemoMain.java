package com.cqhacks.racelive.demo;

import java.nio.file.Path;
import java.util.List;

/**
 * Logic equivalent to the team's {@code Main.main()} — reads LapTimes.csv and prints the first race summary.
 * <p>
 * Original (root {@code Main.java}):
 * <pre>
 * LapTimesReader LTReader = new LapTimesReader(".../LapTimes.csv");
 * ArrayList&lt;Race&gt; races = LTReader.readCSV();
 * System.out.println(races.get(0).toString());
 * </pre>
 */
public final class CsvDemoMain {

    private CsvDemoMain() {}

    public static String firstRaceSummary(Path lapTimesCsv) {
        LapTimesReader reader = new LapTimesReader(lapTimesCsv.toString());
        List<Race> races = reader.readCSV();
        if (races.isEmpty()) {
            return "No races parsed from LapTimes.csv (check path and file).";
        }
        return races.get(0).toString();
    }

    /** Prefer race whose round matches the session; fallback to first race. */
    public static String summaryForRound(Path lapTimesCsv, int round) {
        LapTimesReader reader = new LapTimesReader(lapTimesCsv.toString());
        List<Race> races = reader.readCSV();
        if (races.isEmpty()) {
            return "No races parsed from LapTimes.csv.";
        }
        for (Race race : races) {
            if (race.getRounds() == round) {
                return race.toString();
            }
        }
        return "No race with round " + round + " in CSV; showing first race:\n\n" + races.get(0).toString();
    }
}
