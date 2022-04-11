package org.parts.mp.wrapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 重构mybatsi plus QueryWrapper
 * <p>
 * Function参数类型无需与T类型一致，条件字段取自Function封装字段名称。
 * </p>
 * <p>
 * <b style="color:red">应用场景：A(f2,f3)继承B(f1),查询A中f1等于1时可编写为：new
 * QueryWrapperLa&lt;A&gt;().eqLa(B:getF1,1);</b>
 * </p>
 *
 * @param <T>
 * @author ltl
 */
public class QueryWrapperLa<T> extends QueryWrapper<T> {

    private static final long serialVersionUID = -1472223070469219546L;
    private static final String writeReplace = "writeReplace";
    private static Map<String, SerializedLambda> EFUN_CACHE = new HashMap<String, SerializedLambda>();

    /**
     * where条件，使用“=”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @return
     */
    public QueryWrapperLa<T> eqLa(FFunction<T, ?> column, Object val) {
        return (QueryWrapperLa<T>) super.eq(gf(column, false), val);
    }

    /**
     * where条件，使用“=”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @param boo    封装方法对应的变量是否存在
     * @return
     */
    public QueryWrapperLa<T> eqLa(FFunction<T, ?> column, Object val, boolean boo) {
        return (QueryWrapperLa<T>) super.eq(gf(column, boo), val);
    }

    /**
     * where条件，使用“<>”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @return
     */
    public QueryWrapperLa<T> neLa(FFunction<T, ?> column, Object val) {
        return (QueryWrapperLa<T>) super.ne(gf(column, false), val);
    }

    /**
     * where条件，使用“<>”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @param boo    封装方法对应的变量是否存在
     * @return
     */
    public QueryWrapperLa<T> neLa(FFunction<T, ?> column, Object val, boolean boo) {
        return (QueryWrapperLa<T>) super.ne(gf(column, boo), val);
    }

    /**
     * where条件，使用“like %?%”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @return
     */
    public QueryWrapperLa<T> likeLa(FFunction<T, ?> column, Object val) {
        return (QueryWrapperLa<T>) super.like(gf(column, false), val);
    }

    /**
     * where条件，使用“like %?%”进行拼接(lambda表达式方式)
     *
     * @param column 变量的封装方法
     * @param val    查询条件
     * @param boo    封装方法对应的变量是否存在
     * @return
     */
    public QueryWrapperLa<T> likeLa(FFunction<T, ?> column, Object val, boolean boo) {
        return (QueryWrapperLa<T>) super.like(gf(column, boo), val);
    }

    /**
     * 根据方法获取变量名称
     *
     * @param efun      变量get方法
     * @param attribute 变量相对于当前类是否真实存在
     * @return
     */
    private String gf(FFunction<T, ?> efun, boolean attribute) {
        String clazzName = efun.getClass().getCanonicalName();
        SerializedLambda serializedLambda = Optional.ofNullable(EFUN_CACHE.get(clazzName)).orElseGet(() -> {
            // 从function取出序列化方法
            Method writeReplaceMethod;
            try {
                writeReplaceMethod = efun.getClass().getDeclaredMethod(writeReplace);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            // 从序列化方法取出序列化的lambda信息
            boolean isAccessible = writeReplaceMethod.isAccessible();
            writeReplaceMethod.setAccessible(true);
            try {
                return (SerializedLambda) writeReplaceMethod.invoke(efun);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } finally {
                writeReplaceMethod.setAccessible(isAccessible);
            }
        });
        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
        if (attribute) {
            Field field;
            try {
                field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
            } catch (ClassNotFoundException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            // 从field取出字段名，可以根据实际情况调整
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && tableField.value().length() > 0) {
                return tableField.value();
            }
        }
        return fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
