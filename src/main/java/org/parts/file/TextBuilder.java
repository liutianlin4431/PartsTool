package org.parts.file;

/**
 * @description: 文本构建器：非线程安全；提供换行文本拼接功能
 * @date: 2020/12/15 15:12
 * @auther: 刘天林
 * @version: 1.0
 */
public class TextBuilder {
    private StringBuilder sb = null;

    public TextBuilder() {
        if (sb == null) {
            sb = new StringBuilder();
        }
    }

    /**
     * 拼接文本
     *
     * @param csq
     * @return
     */
    public TextBuilder append(CharSequence csq) {
        sb.append(csq);
        return this;
    }

    /**
     * 拼接文本并换行
     *
     * @param csq
     * @return
     */
    public TextBuilder appendLn(CharSequence csq) {
        sb.append(csq).append("\n");
        return this;
    }

    public int length() {
        return sb.length();
    }

    public String toString() {
        return sb.toString();
    }
}
