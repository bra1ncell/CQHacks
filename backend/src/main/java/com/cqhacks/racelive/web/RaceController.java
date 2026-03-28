package com.cqhacks.racelive.web;

import com.cqhacks.racelive.service.RaceDataService;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RaceController {

    private final RaceDataService raceDataService;

    public RaceController(RaceDataService raceDataService) {
        this.raceDataService = raceDataService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/race-results")
    public ResponseEntity<?> raceResults() {
        try {
            return ResponseEntity.ok(raceDataService.loadRaceResults());
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/lap-times")
    public ResponseEntity<?> lapTimes(
            @RequestParam int round,
            @RequestParam(defaultValue = "500") int limit) {
        if (limit < 1 || limit > 10_000) {
            return ResponseEntity.badRequest().body(Map.of("error", "limit must be between 1 and 10000"));
        }
        try {
            return ResponseEntity.ok(raceDataService.loadLapTimesForRound(round, limit));
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
