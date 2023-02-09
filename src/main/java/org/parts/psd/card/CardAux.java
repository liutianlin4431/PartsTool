package org.parts.psd.card;

/**
 * 位数卡的辅助工具
 * 
 * @author liuti
 *
 */
@SuppressWarnings("deprecation")
public class CardAux {
	/**
	 * 卡中添加浮点数 <br/>
	 * 起始位置与结束位置与卡的说明手册一致，数值为“正整数”。<br/>
	 * 
	 * @param cardB  卡的字节数组
	 * @param source 添加的浮点数
	 * @param goalS  存放的起始位置（包含自身）
	 * @param goalE  存放的结束位置（包含自身）
	 */

	public static void addDouble(byte[] cardB, Double source, int goalS, int goalE) {
		if (goalE > goalS && goalS > 0 && goalE > 0) {
			CardUtils.doubleScaleAddByte(cardB, source, goalS - 1, goalE - 1);
		}
	}

	/**
	 * 卡中添加数据 <br/>
	 * 起始位置与结束位置与卡的说明手册一致，数值为“正整数”。<br/>
	 * 
	 * @param cardB  卡的字节数组
	 * @param source 添加的浮点数
	 * @param goalS  存放的起始位置（包含自身）
	 * @param goalE  存放的结束位置（包含自身）
	 */
	public static void addVal(byte[] cardB, Object source, int goalS, int goalE) {
		if (goalE > goalS && goalS > 0 && goalE > 0) {
			CardUtils.addByte(cardB, source, goalS - 1, goalE - 1);
		}
	}
}
