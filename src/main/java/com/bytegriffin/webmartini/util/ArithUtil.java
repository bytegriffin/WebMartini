package com.bytegriffin.webmartini.util;

import java.math.BigDecimal;

public final class ArithUtil {
	
	/**
	 *	去掉小数点
	 * @param datastr
	 * @return
	 */
	public static int getIntValue(String datastr){
		BigDecimal mData = new BigDecimal(datastr).setScale(0, BigDecimal.ROUND_HALF_UP);
		return mData.intValue();
	}

}
