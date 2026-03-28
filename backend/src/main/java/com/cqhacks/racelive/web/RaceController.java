package com.cqhacks.racelive.web;

import com.cqhacks.racelive.service.DemoMainService;
import com.cqhacks.racelive.service.RaceDataService;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RaceController {

    private final RaceDataService raceDataService;
    private final DemoMainService demoMainService;

    public RaceController(RaceDataService raceDataService, DemoMainService demoMainService) {
        this.raceDataService = raceDataService;
        this.demoMainService = demoMainService;
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

    /** Team Main.java reference + ported CsvDemoMain source (race session UI). */
    @GetMapping(value = "/demo/main-source", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> demoMainSource() throws IOException {
        String legacy = readDemoResource("demo/OriginalMain.java.txt");
        String ported = readDemoResource("demo/CsvDemoMain.java");
        String body =
                "/* ─── Original team Main.java (reference) ─── */\n\n" + legacy + "\n\n/* ─── Ported: CsvDemoMain.java (Spring Boot) ─── */\n\n" + ported;
        return ResponseEntity.ok(body);
    }

    /** Same idea as {@code System.out.println(races.get(0))} using LapTimes.csv. */
    @GetMapping(value = "/demo/main-output", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> demoMainOutput(@RequestParam(defaultValue = "1") int round) {
        return ResponseEntity.ok(demoMainService.runDemoForRound(round));
    }

    private static String readDemoResource(String path) throws IOException {
        ClassPathResource res = new ClassPathResource(path);
        if (!res.exists()) {
            return "// (missing resource: " + path + ")\n";
        }
        return res.getContentAsString(StandardCharsets.UTF_8);
    }
}
