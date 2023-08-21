package applications;

import java.time.DayOfWeek;
import java.time.YearMonth;

public record RecordArguments(int month, int year, DayOfWeek firstDay) {

    public DayOfWeek getFirstDay() {
        return firstDay;
    }

    public int getMonthDays() {
        YearMonth ym = YearMonth.of(year, month);
        return ym.lengthOfMonth();
    }

    public int getFirstMonthWeekDay() {
        YearMonth ym = YearMonth.of(year, month);
        DayOfWeek firstDayOfMonth = ym.atDay(1).getDayOfWeek();
        int daysUntilFirstDayOfWeek = (firstDayOfMonth.getValue() + 7 - firstDay.getValue()) % 7;
        return (daysUntilFirstDayOfWeek + 1);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
