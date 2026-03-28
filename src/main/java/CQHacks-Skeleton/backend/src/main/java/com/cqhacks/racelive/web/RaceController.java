package com.cqhacks.racelive.web;

import com.cqhacks.racelive.model.Race;
import com.cqhacks.racelive.service.RaceDataService;
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

    /** Simple health check. */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    /**
     * GET /api/races
     * Returns all races with driver identity and result/qualifying data.
     * No per-lap data is included — use /api/lap-times for that.
     */
    @GetMapping("/races")
    public ResponseEntity<?> races() {
        try {
            List<Race> results = raceDataService.loadRaceResults();
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/lap-times?round=1&limit=500
     * Returns a single race object containing all drivers with their lap data.
     *
     * @param round  required — the race round number
     * @param limit  optional — max total laps to return (default 500, max 10000)
     */
    @GetMapping("/lap-times")
    public ResponseEntity<?> lapTimes(
            @RequestParam int round,
            @RequestParam(defaultValue = "500") int limit) {

        if (limit < 1 || limit > 10_000) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "limit must be between 1 and 10000"));
        }
        try {
            Race race = raceDataService.loadLapTimesForRound(round, limit);
            return ResponseEntity.ok(race);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
