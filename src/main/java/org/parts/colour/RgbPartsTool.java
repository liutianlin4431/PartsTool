package org.parts.colour;

public class RgbPartsTool {

    /**
     * INT转RGB
     *
     * @param argb
     * @return
     */
    public static String IntConverRgb(Integer argb) {
        Integer rgb[] = new Integer[3];
        rgb[0] = (argb & 0xff0000) >> 16;
        rgb[1] = (argb & 0xff00) >> 8;
        rgb[2] = (argb & 0xff);
        return "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")";
    }

    /**
     * RGB转INT
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static Integer RgbConverInt(Integer r, Integer g, Integer b) {
        Integer color = ((0xFF << 24) | (r << 16) | (g << 8) | b);
        return color;
    }

    /**
     * 提取RGB三原色 0:r; 1:g; 2:b;
     *
     * @param RGB
     * @return
     */
    public static Integer[] InterceptRGB(String RGB) {
        Integer rgb[] = new Integer[3];
        RGB = RGB.toLowerCase();
        RGB = RGB.replace("rgb(", "").replace(")", "");
        String rgbStr[] = RGB.split(",");
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = Integer.valueOf(rgbStr[i]);
        }
        return rgb;
    }

    /**
     * 颜色转INT（RGB | 16进制带# | INT）
     *
     * @param colourObj
     * @return
     */
    public static Integer colourToInt(Object colourObj) {
        Integer colour = 0;
        if (colourObj == null) {
            return 0;
        }
        String colourStr = colourObj + "";
        if (colourStr.contains(",")) {
            // 判断颜色是否为RGB格式
            Integer rgb[] = InterceptRGB(colourStr);
            colour = RgbConverInt(rgb[0], rgb[1], rgb[2]);
        } else if (colourStr.startsWith("#")) {
            // 判断颜色是否为16进制格式
            String rgb = colourStr.substring(1, colourStr.length());
            colour = Integer.parseInt(rgb, 16);
        }
        //判断colourStr是否为数字
        else if (colourStr.matches("^[0-9]*$")) {
            // 判断颜色是否为数字类型
            colour = Integer.valueOf(colourStr);
        }
        return colour;
    }

}
