package ru.yandex.practicum.sleeptracker;

import java.util.List;

public interface SleepAnalysisFunction {
    String getDescription();

    Object apply(List<SleepingSession> sessions);
}