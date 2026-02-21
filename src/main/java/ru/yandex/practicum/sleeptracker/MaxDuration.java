package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxDuration implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Maximum sleep duration (minutes)";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0);
    }
}