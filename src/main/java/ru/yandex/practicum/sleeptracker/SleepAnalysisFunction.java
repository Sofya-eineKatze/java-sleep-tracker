package ru.yandex.practicum.sleeptracker;

import java.util.List;

public interface SleepAnalysisFunction<T> {
    String getDescription();
    T apply(List<SleepingSession> sessions);
}