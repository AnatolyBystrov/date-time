package applications;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {
    private static final int TITLE_OFFSET = 10;
    private static final int WEEK_DAYS_OFFSET = 2;
    private static final int COLUMN_WIDTH = 4;
    private static final Locale LOCALE = Locale.getDefault();

    public static void main(String[] args) {
        try {
            RecordArguments recordArguments = getRecordArguments(args);
            printCalendar(recordArguments);

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(RecordArguments recordArguments) throws Exception {
        printTitle(recordArguments);
        printWeekDays(recordArguments.getFirstDay());
        printDays(recordArguments);
    }

    private static void printDays(RecordArguments recordArguments) {
        int nDays = recordArguments.getMonthDays();
        int currentWeekDay = recordArguments.getFirstMonthWeekDay();
        System.out.printf("%s", " ".repeat(getFirstColumnOffset(currentWeekDay)));
        for (int day = 1; day <= nDays; day++) {
            System.out.printf("%4d", day);

            if (currentWeekDay == 7) {
                currentWeekDay = 0;
                System.out.println();
            }
            currentWeekDay++;
        }
    }

    private static int getFirstColumnOffset(int currentWeekDay) {
        return COLUMN_WIDTH * (currentWeekDay - 1);
    }

    private static void printWeekDays(DayOfWeek firstDayOfWeek) {
        DayOfWeek[] orderedWeekDays = getOrderedWeekDays(firstDayOfWeek);
        for (DayOfWeek currentDay : orderedWeekDays) {
            System.out.printf("%s%s", " ".repeat(WEEK_DAYS_OFFSET), currentDay.getDisplayName(TextStyle.SHORT, LOCALE));
        }
        System.out.println();
    }

    private static DayOfWeek[] getOrderedWeekDays(DayOfWeek firstDayOfWeek) {
        DayOfWeek[] weekDays = DayOfWeek.values();
        DayOfWeek[] orderedWeekDays = new DayOfWeek[7];
        int startIdx = firstDayOfWeek.getValue() - 1;
        for (int i = 0; i < 7; i++) {
            orderedWeekDays[i] = weekDays[startIdx];
            startIdx = (startIdx + 1) % 7;
        }
        return orderedWeekDays;
    }

    private static void printTitle(RecordArguments recordArguments) {
        Month monthEn = Month.of(recordArguments.getMonth());
        System.out.printf("%s%s  %d\n", " ".repeat(TITLE_OFFSET), monthEn.getDisplayName(TextStyle.FULL, LOCALE), recordArguments.getYear());
    }

    private static RecordArguments getRecordArguments(String[] args) throws Exception {
        int month = getMonthArg(args);
        int year = getYearArg(args);
        DayOfWeek dayOfWeek = getFirstDayOfWeek(args);
        return new RecordArguments(month, year, dayOfWeek);
    }

    private static DayOfWeek getFirstDayOfWeek(String[] args) {
        if (args.length > 2) {
            return DayOfWeek.valueOf(args[2].toUpperCase());
        }
        return DayOfWeek.SATURDAY;
    }

    private static int getMonthArg(String[] args) throws Exception {
        int monthRes = LocalDate.now().getMonthValue();
        if (args.length > 0) {
            try {
                monthRes = Integer.parseInt(args[0]);
                if (monthRes < 1 || monthRes > 12) {
                    throw new Exception("Invalid month value");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Month value must be a number");
            }
        }
        return monthRes;
    }

    private static int getYearArg(String[] args) throws Exception {
        int yearRes = LocalDate.now().getYear();
        if (args.length > 1) {
            try {
                yearRes = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new Exception("Year must be a number");
            }
        }
        return yearRes;
    }
}
