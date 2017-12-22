package com.zfk.base.util;

import java.math.BigDecimal;

public class MathUtils {
	public static final BigDecimal ZERO_DECIMAL = new BigDecimal(0);

	public static double roundHalfUp(BigDecimal decimal) {
		return roundHalfUp(decimal, 2);
	}

	public static double roundHalfUp(BigDecimal decimal, int scale) {
		return decimal.setScale(scale, 4).doubleValue();
	}

	public static double roundHalfUp(double value, int scale) {
		BigDecimal decimal = BigDecimal.valueOf(value);
		return decimal.setScale(scale, 4).doubleValue();
	}

	public static double roundHalfUp(double value) {
		return roundHalfUp(value, 2);
	}

	public static double doubleAdd(double... value) {
		double sum = 0.0D;
		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				sum += value[i];
			}
		}
		return roundHalfUp(sum);
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.MathUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */