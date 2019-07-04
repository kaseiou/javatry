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

import java.io.File;
import java.util.*;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.bizfw.colorbox.impl.StandardColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBoxTextNotFoundException;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.SecretBox;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, not using Stream API. <br>
 * Show answer by log() for question of javadoc. <br>
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author Kasei Ou
 */
public class Step11ClassicStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * How many lengths does color name of first color-boxes have? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            ColorBox colorBox = colorBoxList.get(0);
            BoxColor boxColor = colorBox.getColor();
            String colorName = boxColor.getColorName();
            int answer = colorName.length();
            log(answer + " (" + colorName + ")"); // also show name for visual check
        } else {
            log("*not found");
        }
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        int maxLength = -1;
        String maxLengthString = null;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    int currentLength = contentString.length();
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        maxLengthString = contentString;
                    }
                }
            }
        }
        if (maxLength >= 0) {
            log(maxLengthString, maxLength);
        }
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        int maxLength = -1;
        int minLength = Integer.MAX_VALUE;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    int currentLength = contentString.length();
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                    }
                    if (currentLength < minLength) {
                        minLength = currentLength;
                    }
                }
            }
        }
        if (maxLength >= minLength) {
            log(maxLength - minLength, maxLength, minLength);
        }
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        int maxLength = -1;
        int subMaxLength = -1;
        String maxString = null;
        String subMaxString = null;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (null != content) {
                    String contentString = content.toString();
                    int currentLength = contentString.length();
                    if (currentLength > maxLength) {
                        subMaxLength = maxLength;
                        subMaxString = maxString;
                        maxLength = currentLength;
                        maxString = contentString;
                    } else if (currentLength > subMaxLength) {
                        subMaxLength = currentLength;
                        subMaxString = contentString;
                    }
                }
            }
        }
        if (null != subMaxString) {
            log(subMaxString, subMaxLength);
        }
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        int sumOfLength = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    sumOfLength += contentString.length();
                }
            }
        }
        log(sumOfLength);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        int maxLength = -1;
        List<String> maxLengthStrings = new ArrayList<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            String colorName = colorBox.getColor().getColorName();
            int currentLength = colorName.length();
            if (currentLength > maxLength) {
                maxLengthStrings.clear();
                maxLengthStrings.add(colorName);
                maxLength = currentLength;
            } else if (currentLength == maxLength) {
                maxLengthStrings.add(colorName);
            }
        }
        if (maxLength >= 0) {
            log(maxLength);
            for (String str : maxLengthStrings) {
                log(str);
            }
        }
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        final String prefix = "Water";
        List<String> answerList = new ArrayList<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    if (contentString.startsWith(prefix)) {
                        answerList.add(colorBox.getColor().getColorName());
                        break;
                    }
                }
            }
        }
        for (String str : answerList) {
            log(str);
        }
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        final String suffix = "front";
        List<String> answerList = new ArrayList<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    if (contentString.endsWith(suffix)) {
                        answerList.add(colorBox.getColor().getColorName());
                        break;
                    }
                }
            }
        }
        for (String str : answerList) {
            log(str);
        }
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        final String suffix = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    if (contentString.endsWith(suffix)) {
                        int answer = contentString.lastIndexOf(suffix) + 1;
                        log(answer);
                    }
                }
            }
        }
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        final String pattern = "ど";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    int firstIndex = contentString.indexOf(pattern);
                    int lastIndex = contentString.lastIndexOf(pattern);
                    if (firstIndex != lastIndex) {
                        int answer = lastIndex + 1;
                        log(answer);
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        final String suffix = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    if (contentString.endsWith(suffix)) {
                        String answer = contentString.substring(0, 1);
                        log(answer);
                    }
                }
            }
        }
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        final String prefix = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentString = (String) content;
                    if (contentString.startsWith(prefix)) {
                        int length = contentString.length();
                        String answer = contentString.substring(length - 1, length);
                        log(answer);
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        final char key = 'o';
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    int count = 0;
                    String contentString = (String) content;
                    for (int i = 0; i < contentString.length(); i++) {
                        if (key == contentString.charAt(i)) {
                            count += 1;
                        }
                    }
                    if (count > 0) {
                        int answer = contentString.length() - count;
                        log(answer);
                    }
                }
            }
        }
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof File) {
                    File contentFile = (File) content;
                    String answerString = contentFile.getPath().replaceAll("/", "\\\\");
                    log(answerString);
                }
            }
        }
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        int sum = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof DevilBox) {
                    DevilBox devilBox = (DevilBox) content;
                    devilBox.wakeUp();
                    devilBox.allowMe();
                    devilBox.open();
                    try {
                        String text = devilBox.getText();
                        sum += text.length();
                    } catch (DevilBoxTextNotFoundException ex) {
                        log("Devil Box Text Not Found!");
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
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Map) {
                    Map contentMap = (Map) content;
                    String mapString = convertMap2String(contentMap, false);
                    log(mapString);
                }
            }
        }
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Map) {
                    Map contentMap = (Map) content;
                    String mapString = convertMap2String(contentMap, true);
                    log(mapString);
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
        final String targetColorString = "white";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals(targetColorString) && colorBox instanceof StandardColorBox) {
                StandardColorBox standardColorBox = (StandardColorBox) colorBox;
                Object object = standardColorBox.getUpperSpace().getContent();
                if (object instanceof SecretBox) {
                    SecretBox secretBox = (SecretBox) object;
                    String text = secretBox.getText();
                    Map map = parseString2Map(text, false);
                    log(text);
                    log(map.toString());
                }
            }
        }
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
        final String targetColorString = "white";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals(targetColorString) && colorBox instanceof StandardColorBox) {
                StandardColorBox standardColorBox = (StandardColorBox) colorBox;
                Object middleObject = standardColorBox.getMiddleSpace().getContent();
                Object lowerObject = standardColorBox.getLowerSpace().getContent();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("middle", middleObject);
                objectMap.put("lower", lowerObject);
                for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                    if (entry.getValue() instanceof SecretBox) {
                        SecretBox secretBox = (SecretBox) entry.getValue();
                        String text = secretBox.getText();
                        Map map = parseString2Map(text, true);
                        log(entry.getKey());
                        log(text);
                        log(map.toString());
                    }
                }
            }
        }
    }

    private String convertMap2String(Map map, boolean isNested) {
        StringBuilder mapString = new StringBuilder();
        Set keySet = map.keySet();
        int length = keySet.size();
        int index = 0;
        mapString.append("map:{");
        for (Object key : keySet) {
            Object value = map.get(key);
            if (isNested && value instanceof Map) {
                mapString.append(" ").append(key).append(" = ").append(convertMap2String((Map) value, true)).append(" ");
            } else {
                mapString.append(" ").append(key).append(" = ").append(value).append(" ");
            }
            if (index < length - 1) {
                mapString.append(";");
            }
            index++;
        }
        mapString.append("}");
        return mapString.toString();
    }

    private Map<String, Object> parseString2Map(String text, boolean isNested) {
        Map<String, Object> map = new HashMap<>();
        LinkedList<String> keyList = new LinkedList<>();
        LinkedList<Object> valueList = new LinkedList<>();
        int pos = 0;
        boolean innerFlag = false;
        int nestedCount = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
            case '{':
                if (innerFlag) {
                    nestedCount += 1;
                } else {
                    innerFlag = true;
                    pos = i + 2;
                }
                break;
            case '}':
                if (nestedCount > 0) {
                    nestedCount -= 1;
                } else if (innerFlag) {
                    if (isNested && '}' == text.charAt(i - 2)) {
                        valueList.add(parseString2Map(text.substring(pos, i - 1), true));
                    } else {
                        valueList.add(text.substring(pos, i - 1));
                    }
                }
                break;
            case ';':
                if (innerFlag && 0 == nestedCount) {
                    if (isNested && '}' == text.charAt(i - 2)) {
                        valueList.add(parseString2Map(text.substring(pos, i - 1), true));
                    } else {
                        valueList.add(text.substring(pos, i - 1));
                    }
                    pos = i + 2;
                }
                break;
            case '=':
                if (innerFlag && 0 == nestedCount) {
                    keyList.add(text.substring(pos, i - 1));
                    pos = i + 2;
                }
                break;
            }
        }
        while (!keyList.isEmpty() && !valueList.isEmpty()) {
            String key = keyList.poll();
            Object value = valueList.poll();
            map.put(key, value);
        }
        return map;
    }
}
