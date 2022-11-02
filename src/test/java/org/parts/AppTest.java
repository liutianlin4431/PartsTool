package org.parts;

import org.junit.Test;
import org.parts.mp.wrapper.JointWrapper;
import org.parts.response.ReturnData;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.lang.Console;

@SuppressWarnings({ "deprecation", "rawtypes" })
public class AppTest {

	@Test
	public void test01() {
		JointWrapper<ReturnData> jw = new JointWrapper<>();
//		jw.or().eq(ReturnData::getData, 1).or().eq(ReturnData::getData, 1);
//		jw.eq(ReturnData::getData, 1);
//		jw.oneGroupOr(1, ReturnData::getCode, ReturnData::getSuccess).or().eq(ReturnData::getData, 2);
		jw.and(a -> {
			a.eq(ReturnData::getData, 1);
		});
		Console.log(jw.getWhereSql());
	}
}
