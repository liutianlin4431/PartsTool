package org.parts.psd.card;

import java.math.BigDecimal;
import java.util.Arrays;

public class CardUtils {
    /**
     * 将浮点数转为DTA中BYTE数据形式；当小数点前一位等于0时则忽略0；±0.1分别表示为：.1/-.1
     *
     * @param d
     * @return
     */
    public static byte[] doubleToByte(Double d) {
        String ds = BigDecimal.valueOf(d).stripTrailingZeros().toPlainString();
        if (!ds.contains(".")) {
            ds += ".";
        }
        try {
            if (Math.abs(d) < 1) {
                if (d < 0) {
                    return ("-" + ds.substring(ds.indexOf("."), ds.length())).getBytes("GBK");
                } else {
                    int index = ds.indexOf(".");
                    if (index >= 0) {
                        return ds.substring(index, ds.length()).getBytes("GBK");
                    }
                }
            }
            return ds.getBytes("GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{(byte) 32};
    }

    /**
     * 将浮点数追加到byte卡片组中指定位置（范围）
     *
     * @param cardB
     * @param source
     * @param goalS  开始位置
     * @param goalE  结束位置
     */
    public static void doubleAddByte(byte[] cardB, Double source, int goalS, int goalE) {
        if (source != null) {
            byte[] sourceB = doubleToByte(source);
            addByte(cardB, sourceB, goalS, goalE);
        }
    }

    /**
     * 浮点数根据指定位置范围进行四舍五入追加到byte卡片组中
     *
     * @param cardB
     * @param source
     * @param goalS  开始位置
     * @param goalE  结束位置
     */
    public static void doubleScaleAddByte(byte[] cardB, Double source, int goalS, int goalE) {
        if (null != source) {
            BigDecimal value = BigDecimal.valueOf(source).stripTrailingZeros();
            String str = value.toPlainString();
            int difference = goalE - goalS + 1;
            if (str.length() > difference) {
                int index = str.indexOf(".") + 1;
                if (index > 0) {
                    int accuracy = difference - index;
                    if (Math.abs(source) < 1) {
                        accuracy += 1;
                    }
                    byte[] sourceB = doubleToByte(value.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue());
                    addByte(cardB, sourceB, goalS, goalE);
                }
            } else {
                doubleAddByte(cardB, source, goalS, goalE);
            }
        }
    }

    /**
     * 将值追加到byte卡片组中的指定位置（范围）
     *
     * @param cardB  卡片
     * @param source 值来源
     * @param goalS  开始位置
     * @param goalE  结束位置
     */
    public static void addByte(byte[] cardB, Object source, int goalS, int goalE) {
        if (source != null) {
            try {
                byte[] sourceB = source.toString().getBytes("GBK");
                addByte(cardB, sourceB, goalS, goalE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将byte数组追加到卡片中
     *
     * @param cardB
     * @param sourceB
     * @param goalS
     * @param goalE
     */
    public static void addByte(byte[] cardB, byte[] sourceB, int goalS, int goalE) {
        try {
            int difference = goalE - goalS + 1;
            if (sourceB != null) {
                if (difference > sourceB.length) {
                    // 当实际值不足长度时，使用空格补充
                    byte[] newByte = new byte[difference];
                    Arrays.fill(newByte, (byte) 32);
                    System.arraycopy(sourceB, 0, newByte, 0, sourceB.length);
                    sourceB = newByte;
                }
                System.arraycopy(sourceB, 0, cardB, goalS, difference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
