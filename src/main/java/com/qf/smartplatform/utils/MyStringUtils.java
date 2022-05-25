package com.qf.smartplatform.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.utils
 * @Description:
 * @Date 2022/5/25 17:58
 */

public class MyStringUtils {
    static String s = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    public static String createRandomString(int length){
        List<Character> list = s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(list);
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            Character character = list.get(new Random().nextInt(list.size()));
            stringBuffer.append(character);
            Collections.shuffle(list);
        }
        return stringBuffer.toString();
    }


}
