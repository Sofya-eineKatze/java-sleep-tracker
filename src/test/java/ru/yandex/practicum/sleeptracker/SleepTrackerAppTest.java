package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private SleepingSession createSession(String start, String end, String quality) {
        return new SleepingSession(
                LocalDateTime.parse(start, FORMATTER),
                LocalDateTime.parse(end, FORMATTER),
                SleepingSession.SleepQuality.valueOf(quality)
        );
    }

    @Test
    void testTotalSessions_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        TotalSessions function = new TotalSessions();
        Integer result = function.apply(sessions);

        assertEquals(3, result);
    }

    @Test
    void testTotalSessions_Empty() {
        List<SleepingSession> sessions = Arrays.asList();

        TotalSessions function = new TotalSessions();
        Integer result = function.apply(sessions);

        assertEquals(0, result);
    }

    @Test
    void testBadQualitySessions_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "BAD"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "BAD"),
                createSession("03.10.25 23:40", "04.10.25 08:00", "NORMAL")
        );

        BadQualitySessions function = new BadQualitySessions();
        Long result = function.apply(sessions);

        assertEquals(2L, result);
    }

    @Test
    void testBadQualitySessions_None() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        BadQualitySessions function = new BadQualitySessions();
        Long result = function.apply(sessions);

        assertEquals(0L, result);
    }

    @Test
    void testAverageSleepDuration_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        AverageSleepDuration function = new AverageSleepDuration();
        Double result = function.apply(sessions);

        assertEquals(318.33, Math.round(result * 100) / 100.0);
    }

    @Test
    void testAverageSleepDuration_SingleSession() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD")
        );

        AverageSleepDuration function = new AverageSleepDuration();
        Double result = function.apply(sessions);

        assertEquals(495.0, result);
    }

    @Test
    void testDaytimeNapCount_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL"),
                createSession("03.10.25 13:30", "03.10.25 14:20", "BAD")
        );

        DaytimeNapCount function = new DaytimeNapCount();
        Long result = function.apply(sessions);

        assertEquals(2L, result);
    }

    @Test
    void testDaytimeNapCount_None() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        DaytimeNapCount function = new DaytimeNapCount();
        Long result = function.apply(sessions);

        assertEquals(0L, result);
    }

    @Test
    void testMinDuration_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        MinDuration function = new MinDuration();
        Long result = function.apply(sessions);

        assertEquals(50L, result);
    }

    @Test
    void testMinDuration_SingleSession() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD")
        );

        MinDuration function = new MinDuration();
        Long result = function.apply(sessions);

        assertEquals(495L, result);
    }

    @Test
    void testMaxDuration_Normal() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        MaxDuration function = new MaxDuration();
        Long result = function.apply(sessions);

        assertEquals(495L, result);
    }

    @Test
    void testMaxDuration_SingleSession() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD")
        );

        MaxDuration function = new MaxDuration();
        Long result = function.apply(sessions);

        assertEquals(495L, result);
    }

    @Test
    void testSleeplessNights_NormalNights() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL"),
                createSession("03.10.25 23:40", "04.10.25 08:00", "BAD")
        );

        SleeplessNights function = new SleeplessNights();
        Long result = function.apply(sessions);

        assertEquals(1L, result);
    }

    @Test
    void testSleeplessNights_WithDayNaps() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("02.10.25 14:10", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:50", "03.10.25 06:40", "NORMAL")
        );

        SleeplessNights function = new SleeplessNights();
        Long result = function.apply(sessions);

        assertEquals(1L, result);
    }

    @Test
    void testSleeplessNights_OneSleeplessNight() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                createSession("03.10.25 23:40", "04.10.25 08:00", "BAD")
        );

        SleeplessNights function = new SleeplessNights();
        Long result = function.apply(sessions);

        assertEquals(2L, result);
    }

    @Test
    void testSleeplessNights_MonthBoundary() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("30.09.25 23:15", "01.10.25 07:30", "GOOD"),
                createSession("01.10.25 23:50", "02.10.25 06:40", "NORMAL"),
                createSession("31.10.25 23:40", "01.11.25 08:00", "BAD")
        );

        SleeplessNights function = new SleeplessNights();
        Long result = function.apply(sessions);

        assertEquals(30L, result);
    }

    @Test
    void testChronotype_Owl() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"),
                createSession("02.10.25 23:45", "03.10.25 09:15", "NORMAL"),
                createSession("03.10.25 23:15", "04.10.25 09:00", "BAD")
        );

        ChronotypeFunction function = new ChronotypeFunction();
        Chronotype result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result);
    }

    @Test
    void testChronotype_Lark() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 21:30", "02.10.25 06:30", "GOOD"),
                createSession("02.10.25 21:15", "03.10.25 06:45", "NORMAL"),
                createSession("03.10.25 21:45", "04.10.25 06:15", "BAD")
        );

        ChronotypeFunction function = new ChronotypeFunction();
        Chronotype result = function.apply(sessions);

        assertEquals(Chronotype.LARK, result);
    }

    @Test
    void testChronotype_Pigeon() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 22:30", "02.10.25 08:00", "GOOD"),
                createSession("02.10.25 23:00", "03.10.25 07:30", "NORMAL"),
                createSession("03.10.25 22:45", "04.10.25 08:15", "BAD")
        );

        ChronotypeFunction function = new ChronotypeFunction();
        Chronotype result = function.apply(sessions);

        assertEquals(Chronotype.PIGEON, result);
    }

    @Test
    void testChronotype_MixedWithTie() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"),
                createSession("02.10.25 21:30", "03.10.25 06:30", "NORMAL"),
                createSession("03.10.25 22:30", "04.10.25 08:00", "BAD")
        );

        ChronotypeFunction function = new ChronotypeFunction();
        Chronotype result = function.apply(sessions);

        assertEquals(Chronotype.PIGEON, result);
    }

    @Test
    void testChronotype_IgnoreDaytimeNaps() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"),
                createSession("02.10.25 14:00", "02.10.25 15:00", "NORMAL"),
                createSession("02.10.25 23:45", "03.10.25 09:15", "NORMAL"),
                createSession("03.10.25 13:30", "03.10.25 14:20", "BAD")
        );

        ChronotypeFunction function = new ChronotypeFunction();
        Chronotype result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result);
    }
}