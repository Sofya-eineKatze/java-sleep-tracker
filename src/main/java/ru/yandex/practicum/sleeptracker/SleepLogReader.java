package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepLogReader {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static List<SleepingSession> readSleepLog(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(SleepLogReader::parseLine)
                    .collect(Collectors.toList());
        }
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid log line: " + line);
        }

        LocalDateTime startTime = LocalDateTime.parse(parts[0], DATE_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(parts[1], DATE_FORMATTER);
        SleepingSession.SleepQuality quality =
                SleepingSession.SleepQuality.valueOf(parts[2]);

        return new SleepingSession(startTime, endTime, quality);
    }
}