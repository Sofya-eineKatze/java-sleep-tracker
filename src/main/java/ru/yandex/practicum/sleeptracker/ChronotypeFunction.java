package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements SleepAnalysisFunction {
    @Override
    public String getDescription() {
        return "Chronotype";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .map(this::determineChronotype)
                .collect(Collectors.groupingBy(
                        chronotype -> chronotype,
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
        int startHour = session.getStartTime().getHour();
        int endHour = session.getEndTime().getHour();

        if (startHour < 22 && endHour < 7) {
            return Chronotype.LARK;
        }

        if (startHour >= 23 && endHour >= 9) {
            return Chronotype.OWL;
        }

        return Chronotype.PIGEON;
    }
}