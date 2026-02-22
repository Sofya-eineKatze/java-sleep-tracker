package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SleepTrackerApp {
    private final List<SleepAnalysisFunction<?>> analysisFunctions;

    public SleepTrackerApp() {
        this.analysisFunctions = initializeFunctions();
    }

    private List<SleepAnalysisFunction<?>> initializeFunctions() {
        return Arrays.asList(
                new TotalSessions(),
                new BadQualitySessions(),
                new AverageSleepDuration(),
                new DaytimeNapCount(),
                new MinDuration(),
                new MaxDuration(),
                new SleeplessNights(),
                new ChronotypeFunction()
        );
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java SleepTrackerApp <path-to-sleep-log-file>");
            return;
        }

        try {
            List<SleepingSession> sessions = SleepLogReader.readSleepLog(args[0]);
            SleepTrackerApp app = new SleepTrackerApp();
            app.runAnalysis(sessions);
        } catch (IOException exception) {
            System.err.println("Error reading file: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.err.println("Error parsing file: " + exception.getMessage());
        }
    }

    private void runAnalysis(List<SleepingSession> sessions) {
        System.out.println("=== Sleep Analysis Results ===\n");

        analysisFunctions.forEach(function -> {
            Object result = function.apply(sessions);
            System.out.println(new SleepAnalysisResult(function.getDescription(), result));
        });
    }
}