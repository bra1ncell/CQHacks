package com.cqhacks.racelive.config;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataPathsProperties {

    private final Path raceResultsCsv;
    private final Path lapTimesCsv;

    public DataPathsProperties(
            @Value("${app.race-results-csv:}") String raceResultsCsv,
            @Value("${app.lap-times-csv:}") String lapTimesCsv) {
        this.raceResultsCsv = resolvePath(raceResultsCsv, Path.of("..", "RaceResults.csv"));
        this.lapTimesCsv = resolvePath(lapTimesCsv, Path.of("..", "LapTimes.csv"));
    }

    private static Path resolvePath(String configured, Path defaultRelativeToBackend) {
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured).toAbsolutePath().normalize();
        }
        return defaultRelativeToBackend.toAbsolutePath().normalize();
    }

    public Path getRaceResultsCsv() {
        return raceResultsCsv;
    }

    public Path getLapTimesCsv() {
        return lapTimesCsv;
    }
}
