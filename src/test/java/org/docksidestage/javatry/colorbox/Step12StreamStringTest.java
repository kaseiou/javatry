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
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.impl.StandardColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBoxTextNotFoundException;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.SecretBox;
import org.docksidestage.unit.PlainTestCase;
import org.javatuples.Pair;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author Kasei Ou
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor().getColorName())
                .map(colorName -> colorName.length() + " (" + colorName + ")")
                .orElse("*not found");
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content).length())
                .max(Comparator.naturalOrder())
                .orElse(-1);
        if (-1 != answer) {
            log(answer);
        }
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int minLength = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content).length())
                .min(Comparator.naturalOrder())
                .orElse(-1);
        int maxLength = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content).length())
                .max(Comparator.naturalOrder())
                .orElse(-1);
        if (minLength != -1 && maxLength != -1) {
            int answer = maxLength - minLength;
            log(answer);
        }
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Pair<Pair<String, Integer>, Pair<String, Integer>> ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content != null)
                .map(content -> content.toString())
                .map(contentString -> new Pair<>(contentString, contentString.length()))
                .map(pair -> new Pair<>(pair, pair))
                .reduce(new Pair<>(new Pair<>(null, -1), new Pair<>(null, -1)), (maxPair, currentPair) -> {
                    Pair<String, Integer> currentStringPair = currentPair.getValue0();
                    Integer currentStringLength = currentStringPair.getValue1();
                    Integer maxLength = maxPair.getValue0().getValue1();
                    Integer subMaxLength = maxPair.getValue1().getValue1();
                    if (currentStringLength > maxLength) {
                        return new Pair<>(currentStringPair, maxPair.getValue0());
                    } else if (currentStringLength > subMaxLength) {
                        return new Pair<>(maxPair.getValue0(), currentStringPair);
                    } else {
                        return maxPair;
                    }
                });
        if (-1 != ans.getValue1().getValue1()) {
            log(ans.getValue1().getValue0());
            log(ans.getValue1().getValue1());
        }
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(contentString -> ((String) contentString).length())
                .reduce(0, (sum, len) -> sum + len);
        log(ans);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .map(colorBox -> colorBox.getColor().getColorName().length())
                .max(Comparator.naturalOrder())
                .orElse(-1);
        if (ans >= 0) {
            log(ans);
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
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .map(colorBox -> new Pair<>(colorBox, colorBox.getSpaceList()
                        .stream()
                        .map(boxSpace -> boxSpace.getContent())
                        .filter(content -> content instanceof String)
                        .map(content -> (String) content)
                        .collect(Collectors.toList())))
                .filter(pair -> pair.getValue1().stream().anyMatch(contentString -> contentString.startsWith(prefix)))
                .map(pair -> pair.getValue0().getColor().getColorName())
                .forEach(colorName -> log(colorName));
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        final String suffix = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .map(colorBox -> new Pair<>(colorBox, colorBox.getSpaceList()
                        .stream()
                        .map(boxSpace -> boxSpace.getContent())
                        .filter(content -> content instanceof String)
                        .map(content -> (String) content)
                        .collect(Collectors.toList())))
                .filter(pair -> pair.getValue1().stream().anyMatch(contentString -> contentString.endsWith(suffix)))
                .map(pair -> pair.getValue0().getColor().getColorName())
                .forEach(colorName -> log(colorName));
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
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(contentString -> contentString.endsWith(suffix))
                .forEach(contentString -> log(contentString.indexOf(suffix) + 1));
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        final String pattern = "ど";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .map(contentString -> new Pair<>(contentString.indexOf(pattern), contentString.lastIndexOf(pattern)))
                .filter(pair -> !pair.getValue0().equals(pair.getValue1()))
                .forEach(pair -> log(pair.getValue1() + 1));
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
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(contentString -> contentString.endsWith(suffix))
                .forEach(contentString -> log(contentString.substring(0, 1)));
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        final String prefix = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(contentString -> contentString.startsWith(prefix))
                .forEach(contentString -> log(contentString.substring(contentString.length() - 1)));
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        final String pattern = "o";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(contentString -> contentString.contains(pattern))
                .map(contentString -> contentString.replaceAll(pattern, ""))
                .forEach(contentString -> log(contentString.length()));
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof File)
                .forEach(content -> log(((File) content).getPath().replaceAll("/", "\\\\")));
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof DevilBox)
                .map(contentDevilBox -> (DevilBox) contentDevilBox)
                .map(devilBox -> {
                    devilBox.wakeUp();
                    devilBox.allowMe();
                    devilBox.open();
                    try {
                        return devilBox.getText().length();
                    } catch (DevilBoxTextNotFoundException ex) {
                        log("Devil Box Text Not Found!");
                        return 0;
                    }
                })
                .reduce(0, (sum, len) -> sum + len);
        log(ans);
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
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Map)
                .forEach(contentMap -> log(convertMap2String((Map) contentMap, false)));
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Map)
                .forEach(contentMap -> log(convertMap2String((Map) contentMap, true)));
    }

    //     ===================================================================================
    //                                                                               Good Luck
    //                                                                               =========
    //     too difficult to be stream?
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Map> mapList = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("white"))
                .filter(colorBox -> colorBox instanceof StandardColorBox)
                .map(colorBox -> ((StandardColorBox) colorBox).getUpperSpace().getContent())
                .filter(contentBox -> contentBox instanceof SecretBox)
                .map(contentBox -> ((SecretBox) contentBox).getText())
                .map(mapString -> parseString2Map(mapString, false))
                .collect(Collectors.toList());
        for (Map map : mapList) {
            log(map.toString());
        }
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Map> mapList = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("white"))
                .filter(colorBox -> colorBox instanceof StandardColorBox)
                .map(colorBox -> (StandardColorBox) colorBox)
                .map(colorBox -> new Pair<>(colorBox.getMiddleSpace(), colorBox.getLowerSpace()))
                .map(pair -> {
                    List<BoxSpace> boxSpaceList = new ArrayList<>();
                    boxSpaceList.add(pair.getValue0());
                    boxSpaceList.add(pair.getValue1());
                    return boxSpaceList;
                })
                .flatMap(boxSpaceList -> boxSpaceList.stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(contentBox -> contentBox instanceof SecretBox)
                .map(contentBox -> ((SecretBox) contentBox).getText())
                .map(mapString -> parseString2Map(mapString, true))
                .collect(Collectors.toList());
        for (Map map : mapList) {
            log(map.toString());
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
                mapString.append(" ").append(key).append(" = ").append(map.get(key)).append(" ");
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
