package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AverageSleepDuration implements SleepAnalysisFunction<Double> {
    @Override
    public String getDescription() {
        return "Average sleep duration (minutes)";
    }

    @Override
    public Double apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0.0);
    }
}