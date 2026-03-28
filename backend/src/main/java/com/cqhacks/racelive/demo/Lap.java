package com.cqhacks.racelive.demo;

/**
 * One lap row from LapTimes.csv (ported from backend team model).
 */
public record Lap(
        String time,
        String lapTime,
        Double lapNumber,
        Double stint,
        String pitOutTime,
        String pitInTime,
        String sector1Time,
        String sector2Time,
        String sector3Time,
        String sector1SessionTime,
        String sector2SessionTime,
        String sector3SessionTime,
        Double speedI1,
        Double speedI2,
        Double speedFL,
        Double speedST,
        boolean personalBest,
        String compound,
        Double tyreLife,
        boolean freshTyre,
        String lapStartTime,
        String lapStartDate,
        String trackStatus,
        Double position,
        boolean deleted,
        String deletedReason,
        boolean fastF1Generated,
        boolean accurate) {}
