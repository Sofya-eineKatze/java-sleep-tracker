package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxDuration implements SleepAnalysisFunction<Long> {
    @Override
    public String getDescription() {
        return "Maximum sleep duration (minutes)";
    }

    @Override
    public Long apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0);
    }
}