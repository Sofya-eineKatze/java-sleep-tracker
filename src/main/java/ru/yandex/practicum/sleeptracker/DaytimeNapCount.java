package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class DaytimeNapCount implements SleepAnalysisFunction<Long> {
    private static final int NOON_HOUR = 12;

    @Override
    public String getDescription() {
        return "Number of daytime naps";
    }

    @Override
    public Long apply(List<SleepingSession> sessions) {
        return sessions.stream()
                .filter(this::isDayNap)
                .count();
    }

    private boolean isDayNap(SleepingSession session) {
        return !session.isNightSession()
                && session.getStartTime().getHour() >= NOON_HOUR;
    }
}