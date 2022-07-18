package org.parts.mp.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.parts.mp.wrapper.auxiliary.AuxiliaryWrapper;
import org.parts.mp.wrapper.auxiliary.FFunction;

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
public class QueryWrapperLa<T> extends QueryWrapper<T> implements AuxiliaryWrapper<T> {

    private static final long serialVersionUID = -1472223070469219546L;


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


}
