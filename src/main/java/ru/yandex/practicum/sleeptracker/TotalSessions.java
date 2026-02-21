package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessions implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Total number of sleep sessions";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        return sessions.size();
    }
}