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

import java.util.List;
import java.util.Optional;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.BoxedPark;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.BoxedResort;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.BoxedStage;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.FavoriteProvider;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of various type with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author Kasei Ou
 */
public class Step15MiscTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Exception
    //                                                                           =========
    /**
     * What class name is throw-able object in color-boxes? <br>
     * (カラーボックスに入っているthrowできるオブジェクトのクラス名は？)
     */
    public void test_throwable() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Throwable) {
                    log(content.getClass());
                }
            }
        }
    }

    /**
     * What message is for exception that is nested by exception in color-boxes? <br>
     * (カラーボックスに入っている例外オブジェクトのネストした例外インスタンスのメッセージは？)
     */
    public void test_nestedException() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Exception) {
                    Exception exception = (Exception) content;
                    log(exception.getCause().getMessage());
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Interface
    //                                                                           =========
    /**
     * What value is returned by justHere() of FavoriteProvider in yellow color-box? <br>
     * (カラーボックスに入っているFavoriteProviderインターフェースのjustHere()メソッドの戻り値は？)
     */
    public void test_interfaceCall() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof FavoriteProvider) {
                    FavoriteProvider provider = (FavoriteProvider) content;
                    log(provider.justHere());
                }
            }
        }
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * What keyword is in BoxedStage of BoxedResort in List in beige color-box? (show "none" if no value) <br>
     * (beigeのカラーボックスに入っているListの中のBoxedResortのBoxedStageのkeywordは？(値がなければ固定の"none"という値を))
     */
    public void test_optionalMapping() {
        final String targetColorString = "beige";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals(targetColorString)) {
                for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                    Object content = boxSpace.getContent();
                    if (content instanceof List) {
                        List list = (List) content;
                        for (Object object : list) {
                            if (object instanceof BoxedResort) {
                                BoxedResort boxedResort = (BoxedResort) object;
                                Optional<BoxedPark> optBoxedPark = boxedResort.getPark();
                                optBoxedPark.ifPresentOrElse(boxedPark -> {
                                    Optional<BoxedStage> optBoxedStage = boxedPark.getStage();
                                    optBoxedStage.ifPresentOrElse(boxedStage -> {
                                        log(boxedStage.getKeyword());
                                    }, () -> {
                                        log("none");
                                    });
                                }, () -> {
                                    log("none");
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What line number is makeEighthColorBox() call in getColorBoxList()? <br>
     * (getColorBoxList()メソッドの中のmakeEighthColorBox()メソッドを呼び出している箇所の行数は？)
     */
    public void test_lineNumber() {
        final String targetMethodName = "getColorBoxList";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox eighthBox = colorBoxList.get(7);
        for (BoxSpace boxSpace : eighthBox.getSpaceList()) {
            Object content = boxSpace.getContent();
            if (content instanceof Throwable) {
                Throwable throwable = (Throwable) content;
                StackTraceElement[] stackTraceElements = throwable.getStackTrace();
                for (StackTraceElement element : stackTraceElements) {
                    if (element.getMethodName().equals(targetMethodName)) {
                        log(element.getLineNumber());
                        return;
                    }
                }
            }
        }
    }
}
