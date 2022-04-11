package org.parts.data_type;

import java.util.Random;

/**
 * 字符串工具类
 */
public class StringPartsUtils {
    /**
     * 判断字符串是否为空，"null" NULL ""皆为空
     *
     * @param str 字符串
     * @return 空返回“true”；非空返回“false”；
     */
    public static Boolean StrIsNull(String str) {
        if (str == null || str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return
     */
    public String randomString(int length) {
        String val = "";
        Random random = new Random();
        // length为几位密码
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
