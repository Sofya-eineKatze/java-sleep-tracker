package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessions implements SleepAnalysisFunction<Integer> {
    @Override
    public String getDescription() {
        return "Total number of sleep sessions";
    }

    @Override
    public Integer apply(List<SleepingSession> sessions) {
        return sessions.size();
    }
}