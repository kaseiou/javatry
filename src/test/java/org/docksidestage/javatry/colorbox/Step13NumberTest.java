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

import java.math.BigDecimal;
import java.util.*;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.size.BoxSize;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author Kasei Ou
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        int count = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Integer) {
                    Integer integer = (Integer) content;
                    if (integer >= 0 && integer <= 54) {
                        count++;
                    }
                }
            }
        }
        log(count);
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        int count = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Number) {
                    BigDecimal nubmer;
                    if (content instanceof BigDecimal) {
                        nubmer = (BigDecimal) content;
                    } else {
                        nubmer = BigDecimal.valueOf(((Number) content).doubleValue());
                    }
                    if (nubmer.compareTo(BigDecimal.valueOf(0)) >= 0 &&
                        nubmer.compareTo(BigDecimal.valueOf(54)) <= 0) {
                        count++;
                    }
                }
            }
        }
        log(count);
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        int maxSize = -1;
        Set<String> colorSet = new HashSet<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Integer) {
                    BoxSize boxSize = boxSpace.getSize();
                    log(boxSize);
                    int size = boxSize.getWidth() * boxSize.getDepth() * boxSize.getHeight();
                    if (size > maxSize) {
                        maxSize = size;
                        colorSet.clear();;
                        colorSet.add(colorBox.getColor().getColorName());
                    } else if (size == maxSize) {
                        colorSet.add(colorBox.getColor().getColorName());
                    }
                }
            }
        }
        log(colorSet, maxSize);
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
        BigDecimal sum = BigDecimal.ZERO;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof List) {
                    List list = (List) content;
                    for (Object object : list) {
                        if (object instanceof BigDecimal) {
                            BigDecimal bigDecimal = (BigDecimal) object;
                            sum = sum.add(bigDecimal);
                        }
                    }
                }
            }
        }
        log(sum);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
        BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);
        List<Object> keys = new ArrayList<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Map) {
                    Map map = (Map) content;
                    boolean valueIsNumber = true;
                    for (Object value : map.values()) {
                        if (!(value instanceof Number)) {
                            valueIsNumber = false;
                        }
                    }
                    if (valueIsNumber) {
                        for (Object key : map.keySet()) {
                            BigDecimal value = BigDecimal.valueOf(((Number) map.get(key)).doubleValue());
                            if (value.compareTo(max) > 0) {
                                max = value;
                                keys.clear();
                                keys.add(key);
                            } else if (0 == value.compareTo(max)) {
                                keys.add(key);
                            }
                        }
                    }
                }
            }
        }
        log(keys);
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        BigDecimal sum = BigDecimal.ZERO;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals("purple")) {
                for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                    Object content = boxSpace.getContent();
                    if (content instanceof Map) {
                        Map map = (Map) content;
                        for (Object value : map.values()) {
                            BigDecimal bigDecimal = null;
                            if (value instanceof Number) {
                                bigDecimal = BigDecimal.valueOf(((Number) value).doubleValue());
                            } else if (value instanceof String) {
                                try {
                                    bigDecimal = BigDecimal.valueOf(Double.valueOf((String) value));
                                } catch (NumberFormatException ex) {
                                    log("Not Number!");
                                }
                            }
                            if (null != bigDecimal) {
                                sum = sum.add(bigDecimal);
                            }
                        }
                    }
                }
            }
        }
        log(sum);
    }
}
