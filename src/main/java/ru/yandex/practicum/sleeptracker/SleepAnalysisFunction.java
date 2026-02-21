package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public interface SleepAnalysisFunction {
    String getDescription();

    Object apply(List<SleepingSession> sessions);
}