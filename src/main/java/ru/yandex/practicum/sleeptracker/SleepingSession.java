package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.Duration;

public class SleepingSession {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final SleepQuality quality;

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
        LocalDateTime start = this.startTime;
        LocalDateTime end = this.endTime;

        if (start.toLocalDate().equals(end.toLocalDate())) {
            return false;
        }

        int startHour = start.getHour();
        int endHour = end.getHour();

        return true;
    }
}