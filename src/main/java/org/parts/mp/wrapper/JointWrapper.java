package org.parts.mp.wrapper;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import org.parts.data_type.StringPartsUtils;
import org.parts.mp.wrapper.auxiliary.AuxiliaryWrapper;
import org.parts.mp.wrapper.auxiliary.FFunction;

import java.util.function.Consumer;

public class JointWrapper<T> implements AuxiliaryWrapper<T> {
    private boolean or = false;
    private StringBuffer sb = new StringBuffer();
    private JSONObject json = new JSONObject();

    /**
     * 获取条件sql
     *
     * @return
     */
    public String getWhereSql() {
        String sql = this.getNotWhereSql().trim();
        if (sql.length() == 0) {
            return "";
        }
        return "where " + sql;
    }

    /**
     * 获取不携带where关键子的sql
     *
     * @return
     */
    private String getNotWhereSql() {
        String sql = sb.toString().trim();
        // 将sql头部的and去掉
        if (sql.startsWith("and")) {
            sql = sql.substring(3);
        }
        // 将sql头部的or去掉
        if (sql.startsWith("or")) {
            sql = sql.substring(2);
        }
        // 将sql尾部的" or "去掉
        if (sql.endsWith(" or")) {
            sql = sql.substring(0, sql.length() - 3);
        }
        return sql;
    }

    /**
     * 拼接or
     *
     * @return
     */
    public JointWrapper<T> or() {
        sb.append("or ");
        or = true;
        return this;
    }

    /**
     * 一组或，and（columns = ? or columns = ? or ...）
     *
     * @param val
     * @param columns
     * @return
     */
    public JointWrapper<T> oneGroupOr(Object val, FFunction<T, ?>... columns) {
        sb.append("and (");
        for (int i = 0; i < columns.length; i++) {
            FFunction<T, ?> column = columns[i];
            String columnStr = this.gf(column, true);
            sb.append(columnStr + " = " + val);
            if (i != columns.length - 1) {
                sb.append(" or ");
            }
        }
        sb.append(") ");
        return this;
    }

    public JointWrapper<T> and(Consumer<JointWrapper<T>> consumer) {
        JointWrapper<T> groupAnd = new JointWrapper<>();
        consumer.accept(groupAnd);
        sb.append("and (");
        sb.append(groupAnd.getNotWhereSql().trim());
        sb.append(") ");
        json.putAll(groupAnd.getJson());
        return this;
    }

    /**
     * 值为null
     *
     * @param column
     * @return
     */
    public JointWrapper<T> isNull(FFunction<T, ?> column) {
        String columnStr = this.gf(column, true);
        append(columnStr, "is", "null");
        return this;
    }

    /**
     * 相等值
     *
     * @param column
     * @param val
     * @return
     */
    public JointWrapper<T> eq(FFunction<T, ?> column, Object val) {
        String columnStr = this.gf(column, true);
        append(columnStr, "=", packing(val));
        return this;
    }

    /**
     * 不等值
     *
     * @param column
     * @param val
     * @return
     */
    public JointWrapper<T> ne(FFunction<T, ?> column, Object val) {
        String columnStr = this.gf(column, true);
        append(columnStr, "<>", packing(val));
        return this;
    }

    /**
     * 任意位置包含值
     *
     * @param column
     * @param val
     * @return
     */
    public JointWrapper<T> like(FFunction<T, ?> column, Object val) {
        String columnStr = this.gf(column, true);
        append(columnStr, "like", packing("%" + val + "%"));
        return this;
    }

    /**
     * 以值开头
     *
     * @param column
     * @param val
     * @return
     */
    public JointWrapper<T> likeLeft(FFunction<T, ?> column, Object val) {
        String columnStr = this.gf(column, true);
        append(columnStr, "like", packing(val + "%"));
        return this;
    }

    /**
     * 以值结尾
     *
     * @param column
     * @param val
     * @return
     */
    public JointWrapper<T> likeRight(FFunction<T, ?> column, Object val) {
        String columnStr = this.gf(column, true);
        append(columnStr, "like", packing("%" + val));
        return this;
    }

    /**
     * 包装值
     *
     * @param val
     * @return
     */
    private String packing(Object val) {
        String str = Convert.toStr(val);
        // 判断当前值是否为正数、负数、小数
        if (str.matches("^[-+]?\\d+\\.?\\d*$")) {
            return str;
        } else {
            if (str.contains("\\")) str = str.replace("\\", "\\\\\\");
            if (str.contains("'")) str = str.replace("'", "\\'");
            return "'" + str + "'";
        }
    }

    /**
     * 条件追加
     *
     * @param column
     * @param operator
     * @param value
     */
    private void append(String column, String operator, String value) {
        if (or) {
            sb.append(column + " " + operator + " " + value + " ");
            or = false;
        } else {
            sb.append("and " + column + " " + operator + " " + value + " ");
        }
        if (!StringPartsUtils.StrIsNull(value)) json.set(column, value);
    }

    /**
     * 获取json对象
     *
     * @return
     */
    JSONObject getJson() {
        return json;
    }

    /**
     * 获取实体对象
     *
     * @return
     */
    public T getEntity(Class<T> clazz) {
        return (T) json.toBean(clazz);
    }
}
