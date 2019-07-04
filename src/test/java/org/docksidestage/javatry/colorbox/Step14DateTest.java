/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Set;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.space.DoorBoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author Kasei Ou
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as slash-separated (e.g. 2019/04/24)? <br>
     * (カラーボックスに入っている日付をスラッシュ区切り (e.g. 2019/04/24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof TemporalAccessor) {
                    TemporalAccessor date = (TemporalAccessor) content;
                    log(formatter.format(date));
                }
            }
        }
    }

    /**
     * What string of toString() is converted to LocalDate from slash-separated date string (e.g. 2019/04/24) in Set in yellow color-box? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
        final String targetColorString = "yellow";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals(targetColorString)) {
                for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                    Object content = boxSpace.getContent();
                    if (content instanceof Set) {
                        Set set = (Set) content;
                        for (Object object : set) {
                            if (object instanceof String) {
                                String dateString = (String) object;
                                try {
                                    log(dateString);
                                    log(formatter.parse(dateString).toString());
                                } catch (DateTimeParseException ex) {
                                    log(dateString + " can not be parsed!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        int sum = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof TemporalAccessor) {
                    LocalDate date = null;
                    if (content instanceof LocalDate) {
                        date = (LocalDate) content;
                    } else if (content instanceof LocalDateTime) {
                        date = ((LocalDateTime) content).toLocalDate();
                    } else {
                        log("Not processed type!");
                    }
                    if (null != date) {
                        sum += date.getMonthValue();
                    }
                }
            }
        }
        log(sum);
    }

    /**
     * What day of week is second-found date in color-boxes added to three days? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        int count = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof TemporalAccessor) {
                    count += 1;
                    LocalDate date = null;
                    if (content instanceof LocalDate) {
                        date = (LocalDate) content;
                    } else if (content instanceof LocalDateTime) {
                        date = ((LocalDateTime) content).toLocalDate();
                    } else {
                        log("Not processed type!");
                    }
                    if (null != date && 2 == count) {
                        date = date.plusDays(3);
                        log(date.getDayOfWeek());
                        return;
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes?   <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        LocalDateTime preDateTime = null;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof TemporalAccessor) {
                    LocalDateTime dateTime = null;
                    if (content instanceof LocalDate) {
                        dateTime = LocalDateTime.of((LocalDate) content, LocalTime.MIN);
                    } else if (content instanceof LocalDateTime) {
                        dateTime = (LocalDateTime) content;
                    } else {
                        log("Not processed type!");
                    }
                    if (null == preDateTime) {
                        preDateTime = dateTime;
                        continue;
                    }
                    Duration duration = Duration.between(dateTime, preDateTime);
                    log(duration.toDays());
                    return;
                }
            }
        }
    }

    /**
     * What date is LocalDate in yellow color-box
     * that is month-added with LocalDateTime's seconds in the same color-box,
     * and is day-added with Long value in red color-box,
     * and is day-added with the first decimal place of BigDecimal that has three (3) as integer in List in color-boxes? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
        log("勘弁してください！（笑）");
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace instanceof DoorBoxSpace) {
                    DoorBoxSpace doorBoxSpace = (DoorBoxSpace) boxSpace;
                    doorBoxSpace.openTheDoor();
                    Object content = boxSpace.getContent();
                    if (content instanceof LocalTime) {
                        LocalTime time = (LocalTime) content;
                        log(time.getSecond());
                    }
                }
            }
        }
    }
}
