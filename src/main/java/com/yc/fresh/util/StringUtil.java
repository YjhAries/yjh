package com.yc.fresh.util;

public class StringUtil {
	/**
	 * 检查是否有空
	 * @param args
	 * @return
	 */
	public static boolean checkNull(String ... args) {
		if (args == null || args.length <= 0) {
			return true;
		}
		
		for (String arg : args ) {
			if (arg == null || "".equals(arg)) {
				return true;
			}
		}
		
		return false;
	}
}
