package com.cqhacks.racelive.service;

import com.cqhacks.racelive.config.DataPathsProperties;
import com.cqhacks.racelive.demo.CsvDemoMain;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class DemoMainService {

    private final DataPathsProperties paths;

    public DemoMainService(DataPathsProperties paths) {
        this.paths = paths;
    }

    public String runDemoForRound(int round) {
        Path lap = paths.getLapTimesCsv();
        Path results = paths.getRaceResultsCsv();
        if (round <= 0) {
            return CsvDemoMain.firstRaceSummary(lap, results);
        }
        return CsvDemoMain.summaryForRound(lap, results, round);
    }
}
