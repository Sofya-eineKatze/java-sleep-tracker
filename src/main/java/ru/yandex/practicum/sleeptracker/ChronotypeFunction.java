package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.LocalTime;

public class ChronotypeFunction implements SleepAnalysisFunction<Chronotype> {
    private static final LocalTime LARK_SLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);
    private static final LocalTime OWL_SLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);

    @Override
    public String getDescription() {
        return "Chronotype";
    }

    @Override
    public Chronotype apply(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .map(this::determineChronotype)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        if (counts.isEmpty()) {
            return Chronotype.PIGEON;
        }

        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long pigeonCount = counts.getOrDefault(Chronotype.PIGEON, 0L);

        if (owlCount > larkCount && owlCount > pigeonCount) {
            return Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > pigeonCount) {
            return Chronotype.LARK;
        } else {
            return Chronotype.PIGEON;
        }
    }

    private Chronotype determineChronotype(SleepingSession session) {
        LocalTime startTime = session.getStartTime().toLocalTime();
        LocalTime endTime = session.getEndTime().toLocalTime();

        if (startTime.isBefore(LARK_SLEEP_BEFORE) && endTime.isBefore(LARK_WAKE_BEFORE)) {
            return Chronotype.LARK;
        }

        if (startTime.isAfter(OWL_SLEEP_AFTER) && endTime.isAfter(OWL_WAKE_AFTER)) {
            return Chronotype.OWL;
        }

        return Chronotype.PIGEON;
    }
}