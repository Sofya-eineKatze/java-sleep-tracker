package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;

public class SleepingSession {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final SleepQuality quality;

    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    public enum SleepQuality {
        GOOD, NORMAL, BAD
    }

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, SleepQuality quality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quality = quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public boolean isNightSession() {
        LocalDateTime sessionStart = this.startTime;
        LocalDateTime sessionEnd = this.endTime;

        LocalDateTime currentNightStart = sessionStart.toLocalDate().atTime(NIGHT_START);
        LocalDateTime currentNightEnd = sessionStart.toLocalDate().atTime(NIGHT_END);

        if (intersects(currentNightStart, currentNightEnd, sessionStart, sessionEnd)) {
            return true;
        }

        LocalDateTime nextNightStart = sessionStart.toLocalDate().plusDays(1).atTime(NIGHT_START);
        LocalDateTime nextNightEnd = sessionStart.toLocalDate().plusDays(1).atTime(NIGHT_END);

        return intersects(nextNightStart, nextNightEnd, sessionStart, sessionEnd);
    }

    private boolean intersects(LocalDateTime nightStart, LocalDateTime nightEnd,
                               LocalDateTime sessionStart, LocalDateTime sessionEnd) {
        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
    }
}