package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDuration implements SleepAnalysisFunction<Long> {
    @Override
    public String getDescription() {
        return "Minimum sleep duration (minutes)";
    }

    @Override
    public Long apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0);
    }
}