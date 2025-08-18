package bartender.bartenderback.global.util;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String format(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : null;
    }

    public static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMAT);
    }
}
