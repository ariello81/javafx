package pl.ryzykowski.javafx.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DatesUtil {

    private DatesUtil() {
    }

    public static boolean validateDates(String dateFrom, String dateTo) {
        try {
            LocalDate dateFromLocalDate = LocalDate.parse(dateFrom);
            LocalDate dateToLocalDate = LocalDate.parse(dateTo);
            if (dateFromLocalDate.isAfter(dateToLocalDate)) {
                return false;
            }
        }
        catch (DateTimeParseException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static List<LocalDate> getAllDatesBetweenTwoDates(String startDate, String endDate) {
        final LocalDate startLocalDate = LocalDate.parse(startDate).isBefore(LocalDate.now()) ? LocalDate.parse(startDate) : LocalDate.now();
        final LocalDate endLocalDate = LocalDate.parse(endDate).isBefore(LocalDate.now()) ? LocalDate.parse(endDate) : LocalDate.now();
        long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate)+1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startLocalDate.plusDays(i))
                .collect(Collectors.toList());
    }

}