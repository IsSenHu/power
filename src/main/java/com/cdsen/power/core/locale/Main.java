package com.cdsen.power.core.locale;

import java.time.*;
import java.time.temporal.ChronoField;

/**
 * @author HuSen
 * create on 2019/9/4 17:32
 */
public class Main {

    public static void main(String[] args) {
        final String line = "\n=====================================================\n";

        System.out.println(line);
        System.out.println("创建一个LocalDate对象并读取其值");
        LocalDate date = LocalDate.of(2019, 9, 5);
        System.out.println(date);

        System.out.println(date.getYear());
        System.out.println(date.getMonth());
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getDayOfWeek());
        System.out.println(date.getDayOfYear());
        System.out.println(date.lengthOfMonth());
        System.out.println(date.isLeapYear());

        System.out.println(line);
        System.out.println("你还可以使用工厂方法从系统时钟中获取当前的日期");
        System.out.println(LocalDate.now());

        System.out.println(line);
        System.out.println("使用TemporalField读取LocalDate的值");
        System.out.println(date.get(ChronoField.YEAR));
        System.out.println(date.get(ChronoField.MONTH_OF_YEAR));
        System.out.println(date.get(ChronoField.DAY_OF_MONTH));

        System.out.println(line);
        System.out.println("创建一个LocalTime对象并读取其值");
        LocalTime time = LocalTime.of(22, 22, 22, 22);
        System.out.println(time);

        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
        System.out.println(time.getNano());

        System.out.println(line);

        System.out.println("LocalDateTime.now()获取的事当前机器的本地时间，toInstant 根据偏移量获取当前的0时时间，偏移量表示本地时间对0时的偏移量，相当于获取我这个时区这个时间对应的0时的时间");
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+08:00:00")));
        System.out.println(Instant.now().atZone(ZoneId.systemDefault()));

        System.out.println(LocalDateTime.now().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.ofHoursMinutesSeconds(0, 0, 0)).toEpochMilli());

        System.out.println(OffsetDateTime.now().getOffset().toString());
    }
}