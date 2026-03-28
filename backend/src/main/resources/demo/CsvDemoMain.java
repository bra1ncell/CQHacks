package com.cqhacks.racelive.demo;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Ported team {@code Main}: LapTimes → merge RaceResults → race summary + {@link InsightGenerator}.
 */
public final class CsvDemoMain {

    private CsvDemoMain() {}

    public static String firstRaceSummary(Path lapTimesCsv, Path raceResultsCsv) {
        return runDemo(lapTimesCsv, raceResultsCsv, 0);
    }

    /** {@code round <= 0} uses the first race in file order. */
    public static String summaryForRound(Path lapTimesCsv, Path raceResultsCsv, int round) {
        return runDemo(lapTimesCsv, raceResultsCsv, round);
    }

    private static String runDemo(Path lapTimesCsv, Path raceResultsCsv, int round) {
        LapTimesReader ltReader = new LapTimesReader(lapTimesCsv.toString());
        ArrayList<Race> races = new ArrayList<>(ltReader.readCSV());
        if (races.isEmpty()) {
            return "No races parsed from LapTimes.csv (check path and file).";
        }

        RaceResultsReader rrReader = new RaceResultsReader(raceResultsCsv.toString());
        rrReader.readCSV(races);

        Race target = null;
        if (round > 0) {
            for (Race r : races) {
                if (r.getRounds() == round) {
                    target = r;
                    break;
                }
            }
        }
        if (target == null) {
            target = races.get(0);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(target.toString());
        sb.append("\n──────── Insights ────────\n");
        InsightGenerator gen = new InsightGenerator(target);
        for (String line : gen.generateAll()) {
            sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
