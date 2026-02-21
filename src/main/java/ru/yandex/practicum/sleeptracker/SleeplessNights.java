package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SleeplessNights implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Number of sleepless nights";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0;
        }

        long nightSessions = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .count();

        LocalDate firstDate = sessions.stream()
                .map(s -> s.getStartTime().toLocalDate())
                .min(LocalDate::compareTo)
                .get();

        LocalDate lastDate = sessions.stream()
                .map(s -> s.getEndTime().toLocalDate())
                .max(LocalDate::compareTo)
                .get();

        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;

        return totalNights - nightSessions;
    }
}