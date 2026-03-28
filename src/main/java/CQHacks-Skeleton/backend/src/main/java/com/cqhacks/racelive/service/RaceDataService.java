package com.cqhacks.racelive.service;

import com.cqhacks.racelive.config.DataPathsProperties;
import com.cqhacks.racelive.model.Driver;
import com.cqhacks.racelive.model.LapTimesReader;
import com.cqhacks.racelive.model.Race;
import com.cqhacks.racelive.model.RaceResultsReader;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RaceDataService {

    private final DataPathsProperties paths;

    public RaceDataService(DataPathsProperties paths) {
        this.paths = paths;
    }

    /**
     * Loads all races from LapTimes.csv, then merges in RaceResults.csv.
     * Returns the fully populated list of Race objects.
     */
    public List<Race> loadAllRaces() throws IOException {
        assertReadable(paths.getLapTimesCsv());
        assertReadable(paths.getRaceResultsCsv());

        List<Race> races = LapTimesReader.read(paths.getLapTimesCsv());
        RaceResultsReader.merge(paths.getRaceResultsCsv(), races);
        return races;
    }

    /**
     * Returns all races with only result/qualifying data — no per-lap data.
     * Suitable for a lightweight race results overview.
     */
    public List<Race> loadRaceResults() throws IOException {
        assertReadable(paths.getRaceResultsCsv());

        // Start with an empty list — RaceResultsReader will create Race objects
        List<Race> races = new java.util.ArrayList<>();
        RaceResultsReader.merge(paths.getRaceResultsCsv(), races);
        return races;
    }

    /**
     * Returns a single race by round number, with full lap data for all drivers,
     * capped to {@code limit} laps total across the race.
     */
    public Race loadLapTimesForRound(int round, int limit) throws IOException {
        assertReadable(paths.getLapTimesCsv());
        assertReadable(paths.getRaceResultsCsv());

        List<Race> races = LapTimesReader.read(paths.getLapTimesCsv());
        RaceResultsReader.merge(paths.getRaceResultsCsv(), races);

        Race race = races.stream()
                .filter(r -> r.getRound() == round)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No data for round " + round));

        // Enforce lap limit by trimming each driver's lap list proportionally
        applyLapLimit(race, limit);
        return race;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Trims lap data so the total across all drivers does not exceed {@code limit}. */
    private void applyLapLimit(Race race, int limit) {
        int total = race.getDrivers().stream()
                .mapToInt(d -> d.getLapData().size())
                .sum();
        if (total <= limit) return;

        // Trim proportionally: each driver keeps (their laps / total) * limit laps
        for (Driver driver : race.getDrivers()) {
            int keep = (int) Math.floor((double) driver.getLapData().size() / total * limit);
            if (driver.getLapData().size() > keep) {
                driver.getLapData().subList(keep, driver.getLapData().size()).clear();
            }
        }
    }

    private static void assertReadable(java.nio.file.Path path) throws IOException {
        if (!java.nio.file.Files.isRegularFile(path)) {
            throw new IOException(
                    "CSV not found: " + path +
                    ". Run the server from the backend/ directory, set " +
                    "RACE_RESULTS_CSV / LAP_TIMES_CSV, or place CSVs in the repo root.");
        }
    }
}
