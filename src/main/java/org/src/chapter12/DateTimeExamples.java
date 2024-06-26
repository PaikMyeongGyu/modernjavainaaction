package org.src.chapter12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class DateTimeExamples {

    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    /**
     * 개인적으로 날짜 관련 비즈니스를 사용할 때 다시 보고, 굳이 외우려고 하지는 말아야겠다는 생각이 드는 파트!
     * 어떤 클래스를 이용하면 어떻게 날짜를 조정할 수 있는지까지만 기억해두고 모르면 책에서 찾아보자.
     *
     * 그래도 굳이 기억을 하자면, Date는 구리다와 LocalXX나 여기서 나오는 모든 객체들은 변경이 불가능하며 다른 값이 나오는 건
     * 값이 바뀌어서가 아니라 새로운 객체를 만들어서 내보내는 것임을 기억하자.
     * 그래서 할당해서 변수에 놓지 않으면 사라지고, 본인에게 연결하는게 아니면, 기존 객체는 값이 바뀌지 않음.
     */
    public static void main(String[] args) {
        useOldDate();
        useLocalDate();
        useTemporalAdjuster();
        useDateFormatter();
    }

    /** Date 타입을 쓰지 않고 다른 걸 쓰는 이유에 관해서 잘 기억하고 넘어가자.
     *  첫번째 줄에서 new Date(114, 2, 18);로 만들어진 건 2014년 3월 18일을 의미한다.
     *  해외 식으로 순서가 뒤바뀐건 그렇다 쳐도, 1900년을 기준으로 하고 0이 1월, 1이 2월을 의미하는 건 좀 불편하다.
     *  결론은 기존 Date 쓰지말고 LocalTime, LocalDate을 사용합시다 쯤으로 보면 될 것 같다.
     */
    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    /**
     * 그냥 여긴 대충 보고 넘겨도 될 것 같다. 어차피 SQL에서 사용하는 Date랑 Time 관계랑 비슷하다.
     */
    private static void useLocalDate() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear(); // 2014
        Month month = date.getMonth(); // MARCH
        int day = date.getDayOfMonth(); // 18
        DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
        int len = date.lengthOfMonth(); // 31 (3월의 길이)
        boolean leap = date.isLeapYear(); // false (윤년이 아님)
        System.out.println(date);

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time);

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt1);

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());

        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }


    private static void useTemporalAdjuster() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(lastDayOfMonth());
        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        // 아래의 NextWorkingDay와 다르게 람다로도 구현이 가능!
        // 그 이유는 인터페이스 내부에 있는 메서드가 단 한 개 있기 때문임.
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
    }

    /**
     *  여기가 그나마 중요한 부분이 아닌가 싶다. TemporalAdjuster 내부에 들어가보면,
     *  하나의 함수 인터페이스가 정의되어있는데, 그 메서드만 람다나 익명 함수로 정의하면 쓸 수 있다.
     *  아래는 상속을 받아서 사용하는 걸 보여주며, 구현하는 내용은 다음 일하는 날이다.
     */
    private static class NextWorkingDay implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);

        System.out.println(date.format(complexFormatter));
    }


}
