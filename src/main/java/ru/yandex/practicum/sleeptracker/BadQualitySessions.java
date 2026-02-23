package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualitySessions implements SleepAnalysisFunction<Long> {
    @Override
    public String getDescription() {
        return "Number of sessions with BAD quality";
    }

    @Override
    public Long apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .filter(session -> session.getQuality() == SleepingSession.SleepQuality.BAD)
                .count();
    }
}