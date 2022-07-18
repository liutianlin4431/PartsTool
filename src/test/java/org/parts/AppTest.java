package org.parts;

import cn.hutool.core.lang.Console;
import org.junit.Test;
import org.parts.mp.wrapper.JointWrapper;
import org.parts.response.ReturnData;

public class AppTest {
    @Test
    public void test01() {
        JointWrapper<ReturnData> jw = new JointWrapper<>();
        jw.eq(ReturnData::getData, 1);
        jw.oneGroupOr(1, ReturnData::getCode, ReturnData::getSuccess);
        Console.log(jw.getWhereSql());
    }
}

