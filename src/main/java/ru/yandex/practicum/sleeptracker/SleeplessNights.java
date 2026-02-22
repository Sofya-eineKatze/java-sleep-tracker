package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class SleeplessNights implements SleepAnalysisFunction<Long> {
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public String getDescription() {
        return "Number of sleepless nights";
    }

    @Override
    public Long apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0L;
        }

        LocalDate firstDate = sessions.stream()
                .map(s -> s.getStartTime().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("No sessions"));

        LocalDate lastDate = sessions.stream()
                .map(s -> s.getEndTime().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("No sessions"));

        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;

        long nightsWithSleep = Stream.iterate(firstDate, date -> date.plusDays(1))
                .limit(totalNights)
                .filter(date -> hasNightSleep(date, sessions))
                .count();

        return totalNights - nightsWithSleep;
    }

    private boolean hasNightSleep(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atTime(NIGHT_START);
        LocalDateTime nightEnd = night.atTime(NIGHT_END);

        return sessions.stream()
                .anyMatch(session -> intersects(nightStart, nightEnd,
                        session.getStartTime(), session.getEndTime()));
    }

    private boolean intersects(LocalDateTime nightStart, LocalDateTime nightEnd,
                               LocalDateTime sessionStart, LocalDateTime sessionEnd) {
        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
    }
}