package com.altratek.altraserver.extensions.util;

/**
 * 二进制辅助类
 *
 * @author linchengnan
 * @change liuwenjun - "2014-02-06" : 增加将flag拆分成二进制数组的方法
 *
 */
public class BitUtil {
	public static boolean isFlagSet(int flag, int pos) {
		if(pos < 0 || pos >= Integer.SIZE){
			throw new IllegalArgumentException(String.format("设置位不是合法的位置：%s", pos));
		}
		return (flag & (1 << pos)) != 0;
	}

	public static int set(int flag, int pos) {
		if(pos < 0 || pos >= Integer.SIZE){
			throw new IllegalArgumentException(String.format("设置位不是合法的位置：%s", pos));
		}
		return flag | (1 << pos);
	}

	public static int unSet(int flag, int pos) {
		if(pos < 0 || pos >= Integer.SIZE){
			throw new IllegalArgumentException(String.format("设置位不是合法的位置：%s", pos));
		}
		return flag & ~(1 << pos);
	}

	public static boolean isFlagSet(long flag, int pos) {
		if(pos < 0 || pos >= Long.SIZE){
			throw new IllegalArgumentException(String.format("设置位不是合法的位置：%s", pos));
		}
		return (flag & (1 << pos)) != 0;
	}

	public static long set(long flag, int pos) {
		if(pos < 0 || pos >= Long.SIZE){
			throw new IllegalArgumentException(String.format("设置位不是合法的位置：%s", pos));
		}
		return flag | (1 << pos);
	}

	/**
	 * 返回以sperator分隔开的01串
	 *
	 * @param flag
	 * @param sperator
	 * @param length
	 * @return
	 */
	public static String getFlagString(int flag, String separator, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(isFlagSet(flag, i) ? '1' : '0').append(separator);
		}
		if (sb.length() > 1 && separator.length() > 0) {
			sb.delete(sb.length() - separator.length(), sb.length());
		}
		return sb.toString();
	}

	/**
	 * 返回固定长度二进制整型数组
	 * @param flag
	 * @param length
	 * @return
	 */
	public static Integer[] getFlagIntegerArray(int flag, int length){
		if(length < 0 || length >= Integer.SIZE){
			throw new IllegalArgumentException(String.format("长度不合法：%s", length));
		}
		Integer[] result = new Integer[length];
		for (int i = 0; i < length; i ++){
			result[i] = flag >> i & 1;
		}
		return result;
	}

}

____________________________________________________________________________________________________

1. Integer.bitCount()

2. insert into  GbItemCount0(UserId, ItemId, Count) Values(111,111,1<<4) ON DUPLICATE KEY UPDATE Count=Count|VALUES(Count);
