package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDuration implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Minimum sleep duration (minutes)";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0);
    }
}