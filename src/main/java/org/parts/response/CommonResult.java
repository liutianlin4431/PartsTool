package org.parts.response;

import cn.hutool.core.lang.Console;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private String code;
    private Boolean success;
    private Exception errorMsg;
    private String msg;
    private T result;
    private Long stamp;

    public void setErrorMsg(Exception errorMsg) {
        Console.error(errorMsg);
        this.errorMsg = errorMsg;
    }

    /**
     * 成功无数据信息
     *
     * @param <T> 返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> Ok() {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(true);
        cr.setMsg("成功");
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 成功并输出【数据】信息 <br>
     * 与result方法功能相同
     *
     * @param result 结果
     * @param <T>    返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> Ok(T result) {
        CommonResult<T> cr = Ok();
        cr.setResult(result);
        return cr;
    }


    /**
     * 失败并无返回值
     *
     * @param <T> 返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> error() {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(false);
        cr.setMsg("失败");
        cr.setStamp(System.currentTimeMillis());
        return cr;

    }

    /**
     * 输出【执行状态】信息
     *
     * @param boo 执行状态
     * @param <T> 返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> success(boolean boo) {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(boo);
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 输出【编号、执行状态】信息
     *
     * @param code 编号
     * @param boo  执行状态
     * @param <T>  返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> success(String code, boolean boo) {
        CommonResult<T> cr = success(boo);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【数据】信息
     *
     * @param <T>    返回值类型
     * @param result 结果
     * @return CommonResult<T>
     * @deprecated 推荐使用 "OK(T result)"
     */
    @Deprecated
    public static <T> CommonResult<T> result(T result) {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(true);
        cr.setResult(result);
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 输出【编号、数据】信息
     *
     * @param <T>    返回值类型
     * @param code   编号
     * @param result 结果
     * @return
     */
    public static <T> CommonResult<T> result(String code, T result) {
        CommonResult<T> cr = result(result);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【Msg】信息
     *
     * @param <T> 返回值类型
     * @param msg 信息
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> msg(String msg) {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(true);
        cr.setMsg(msg);
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 输出【编号、Msg】信息
     *
     * @param <T>  返回值类型
     * @param code 编号
     * @param msg  信息
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> msg(String code, String msg) {
        CommonResult<T> cr = msg(msg);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【Msg、数据】信息
     *
     * @param <T>    返回值类型
     * @param result 结果
     * @param msg    信息
     * @return
     */
    public static <T> CommonResult<T> msg(String msg, T result) {
        CommonResult<T> cr = msg(msg);
        cr.setResult(result);
        return cr;
    }

    /**
     * 输出【编号、Msg、数据】信息
     *
     * @param <T>    返回值类型
     * @param code   编号
     * @param msg    信息
     * @param result 结果
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> msg(String code, String msg, T result) {
        CommonResult<T> cr = msg(code, msg);
        cr.setResult(result);
        return cr;
    }

    /**
     * 输出【数据】信息
     *
     * @param result 结果
     * @param <T>    返回值类型
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> error(T result) {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(false);
        cr.setResult(result);
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 输出【编号，数据】信息
     *
     * @param <T>
     * @param result
     * @param code
     * @return
     */
    public static <T> CommonResult<T> error(T result, String code) {
        CommonResult<T> cr = error(result);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【Exception】信息
     *
     * @param <T> 返回值类型
     * @param e   异常
     * @return
     */
    public static <T> CommonResult<T> error(Exception e) {
        CommonResult<T> cr = new CommonResult<T>();
        cr.setSuccess(false);
        cr.setErrorMsg(e);
        cr.setMsg(e.getMessage());
        cr.setStamp(System.currentTimeMillis());
        return cr;
    }

    /**
     * 输出【编号、Exception】信息
     *
     * @param <T>
     * @param code
     * @param e
     * @return
     */
    public static <T> CommonResult<T> error(String code, Exception e) {
        CommonResult<T> cr = error(e);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【Exception、数据】信息
     *
     * @param <T>
     * @param e
     * @param result
     * @return
     */
    public static <T> CommonResult<T> error(Exception e, T result) {
        CommonResult<T> cr = error(e);
        cr.setResult(result);
        return cr;
    }

    /**
     * 输出【编号、Exception、数据】信息
     *
     * @param <T>
     * @param code
     * @param e
     * @param result
     * @return
     */
    public static <T> CommonResult<T> error(String code, Exception e, T result) {
        CommonResult<T> cr = error(e, result);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【错误Msg】信息
     *
     * @param <T>
     * @param msg
     * @return
     */
    public static <T> CommonResult<T> error(String msg) {
        CommonResult<T> cr = msg(msg);
        cr.setSuccess(false);
        return cr;
    }

    /**
     * 输出【编号、错误Msg】信息
     *
     * @param <T>
     * @param code
     * @param msg
     * @return
     */
    public static <T> CommonResult<T> error(String code, String msg) {
        CommonResult<T> cr = error(msg);
        cr.setCode(code);
        return cr;
    }

    /**
     * 输出【错误Msg、数据】信息
     *
     * @param <T>
     * @param msg
     * @param result
     * @return
     */
    public static <T> CommonResult<T> error(String msg, T result) {
        CommonResult<T> cr = error(msg);
        cr.setResult(result);
        return cr;
    }

    /**
     * 输出【编码、错误Msg、数据】信息
     *
     * @param <T>
     * @param code
     * @param msg
     * @param result
     * @return
     */
    public static <T> CommonResult<T> error(String code, String msg, T result) {
        CommonResult<T> cr = error(code, msg);
        cr.setResult(result);
        return cr;
    }

    /**
     * 输出【ReturnData】对象
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public ReturnData<T> toRd() {
        ReturnData<T> rd = new ReturnData<T>();
        rd.setCode(this.code);
        if (this.errorMsg != null) {
            rd.setMsg(this.errorMsg.getMessage());
        } else {
            rd.setMsg(this.msg);
        }
        if (this.success) {
            rd.setSuccess(1);
        } else {
            rd.setCode(0);
        }
        rd.setData(this.result);
        return rd;
    }
}
