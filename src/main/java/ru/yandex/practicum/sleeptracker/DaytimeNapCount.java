package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class DaytimeNapCount implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Number of daytime naps";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .filter(session -> !session.isNightSession())
                .filter(session -> session.getStartTime().getHour() >= 12)
                .count();
    }
}