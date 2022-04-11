package org.parts.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Deprecated
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReturnData<T> {
    private Object code;
    private String msg;
    private Integer success;
    private T data;


    protected ReturnData() {
    }

    /**
     * @param code    错误编号
     * @param msg     消息
     * @param success 0：失败；1：成功；(旧请求存在【0：成功；1：失败；】的情况)
     * @param data    返回值
     */

    public ReturnData(Object code, Object msg, Integer success, T data) {
        super();
        this.code = code;
        this.msg = msg + "";
        this.success = success;
        this.data = data;
    }

    /**
     * @param code
     * @param msg
     * @param success 0：失败；1：成功；(旧请求存在【0：成功；1：失败；】的情况)
     * @param data
     */

    public static <T> ReturnData<T> create(Object code, Object msg, Integer success, T data) {
        return new ReturnData(code, msg, success, data);
    }

    /**
     * 返回成功
     *
     * @param <T>  返回类型
     * @param code 编码,不同编号方便寻找跳出位置
     * @param data 返回数据
     * @return
     */

    public static <T> ReturnData<T> Ok(Object code, T data) {
        return new ReturnData(code, "成功", 1, data);
    }

    /**
     * 返回成功
     *
     * @param <T>  返回类型
     * @param code 编码,不同编号方便寻找跳出位置
     * @return
     */

    public static <T> ReturnData<T> Ok(Object code) {
        return new ReturnData(code, "成功", 1, null);
    }

    /**
     * 返回失败
     *
     * @param <T>  返回类型
     * @param code 编码,不同编号方便寻找跳出位置
     * @param msg  描述
     * @return
     */

    public static <T> ReturnData<T> Error(Object code, String msg) {
        return new ReturnData(code, msg, 0, null);
    }

    /**
     * 返回失败
     *
     * @param <T>  返回类型
     * @param code 编码,不同编号方便寻找跳出位置
     * @param e    错误信息
     * @return
     */

    public static <T> ReturnData<T> Error(Object code, Exception e) {
        return new ReturnData(code, e.getMessage(), 0, null);
    }
}
